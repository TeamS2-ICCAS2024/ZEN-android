package com.iccas.zen.presentation.tetris.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.presentation.tetris.logic.Spirit.Companion.Empty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min

enum class Hindrance {
    RandomDirection,
    DisableRotation,
    ReverseControl;


}

class GameViewModel : ViewModel() {
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState: State<ViewState> = _viewState

    var hindranceActive = false
    var hindranceBlockCount = 0
    private val _currentBlocks = MutableStateFlow<Pair<Spirit, Spirit>>(Empty to Empty)
    val currentBlocks: StateFlow<Pair<Spirit, Spirit>> = _currentBlocks

    init {
        startHindranceTimer()
    }

    fun dispatch(action: Action) = reduce(viewState.value, action)
    var currentDirection = Direction.Right // 초기 방향

    fun startHindranceTimer() {
        viewModelScope.launch {
            delay(15000) // Initial delay before the first hindrance
            while (true) {
                if (!hindranceActive) {
                    activateHindrance()
                } else {
                    delay(100) // Small delay to prevent a tight loop
                }
                while (hindranceActive) {
                    delay(100) // Wait until the hindrance is deactivated
                }
                delay(15000) // Delay after hindrance is deactivated
            }
        }
    }

    private fun activateHindrance() {
        viewModelScope.launch {
            hindranceActive = true
            hindranceBlockCount = 0
            val hindrance = Hindrance.values().random()
            emit(viewState.value.copy(currentHindrance = hindrance, showHindranceAlert = true))

            // Pause the game before showing the alert
            dispatch(Action.Pause)
            delay(3000) // 3 seconds to show alert

            // Resume the game after the alert is dismissed
            dispatch(Action.Resume)
            hideHindranceAlert()
            emit(viewState.value.copy(showHindranceAlert = false))

            // Log the hindrance activation
            println("Hindrance activated: $hindrance")

            while (hindranceBlockCount < 3) {
                delay(100) // Adjust delay as needed
            }

            hindranceActive = false
            emit(viewState.value.copy(currentHindrance = null))

            // Log the hindrance deactivation
            println("Hindrance deactivated")
        }
    }

    private fun randomizeDirection(): Direction {
        return Direction.values().random()
    }

    fun hideHindranceAlert() {
        _viewState.value = viewState.value.copy(showHindranceAlert = false)
    }

    private fun reduce(state: ViewState, action: Action) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                emit(when (action) {
                    Action.Reset -> {
                        hindranceActive = false
                        hindranceBlockCount = 0
                        startHindranceTimer() // Reset 시 타이머 다시 시작
                        ViewState(
                            gameStatus = GameStatus.Running,
                            isMute = state.isMute
                        )
                    }
                    Action.Pause -> if (state.isRunning) {
                        state.copy(gameStatus = GameStatus.Paused)
                    } else state
                    Action.Resume -> if (state.isPaused) {
                        state.copy(gameStatus = GameStatus.Running)
                    } else state
                    is Action.Move -> run {
                        if (!state.isRunning) return@run state
                        SoundUtil.play(state.isMute, SoundType.Move)

                        val effectiveDirection = when {
                            state.currentHindrance == Hindrance.RandomDirection && action.direction == Direction.Up -> null
                            state.currentHindrance == Hindrance.ReverseControl -> when (action.direction) {
                                Direction.Left -> Direction.Right
                                Direction.Right -> Direction.Left
                                else -> action.direction
                            }
                            else -> action.direction
                        } ?: return@run state // 무효화된 방향키일 경우, 상태 변경 없이 종료

                        val offset = if (state.currentHindrance == Hindrance.RandomDirection) {
                            randomizeDirection().toOffset()
                        } else {
                            effectiveDirection.toOffset()
                        }
                        val spirit = state.spirit.moveBy(offset)
                        if (spirit.isValidInMatrix(state.bricks, state.matrix)) {
                            state.copy(spirit = spirit)
                        } else {
                            state
                        }
                    }
                    Action.Rotate -> run {
                        if (!state.isRunning || state.currentHindrance == Hindrance.DisableRotation) return@run state
                        SoundUtil.play(state.isMute, SoundType.Rotate)
                        val spirit = state.spirit.rotate().adjustOffset(state.matrix)
                        if (spirit.isValidInMatrix(state.bricks, state.matrix)) {
                            state.copy(spirit = spirit)
                        } else {
                            state
                        }
                    }
                    Action.Drop -> run {
                        if (!state.isRunning || state.currentHindrance == Hindrance.RandomDirection) return@run state
                        SoundUtil.play(state.isMute, SoundType.Drop)
                        var i = 0
                        while (state.spirit.moveBy(0 to ++i).isValidInMatrix(state.bricks, state.matrix)) {
                            // nothing to do
                        }
                        val spirit = state.spirit.moveBy(0 to i - 1)
                        state.copy(spirit = spirit)
                    }
                    Action.GameTick -> run {
                        if (!state.isRunning) return@run state

                        if (state.spirit != Empty) {
                            val spirit = state.spirit.moveBy(Direction.Down.toOffset())
                            if (spirit.isValidInMatrix(state.bricks, state.matrix)) {
                                return@run state.copy(spirit = spirit)
                            } else if(spirit.location.any { it.y <= 0 }) {
                                return@run state.copy(gameStatus = GameStatus.GameOver)
                            }
                        }

                        if (state.spirit != Empty && !state.spirit.isValidInMatrix(state.bricks, state.matrix) &&
                            !state.initialSpawn) {
                            return@run state.copy(
                                gameStatus = GameStatus.ScreenClearing
                            ).also {
                                launch {
                                    emit(
                                        clearScreen(state = state).copy(gameStatus = GameStatus.GameOver)
                                    )
                                }
                            }
                        }

                        if (state.initialSpawn) {
                            emit(state.copy(initialSpawn = false))
                        }

                        val (updatedBricks, clearedLines) = updateBricks(
                            state.bricks,
                            state.spirit,
                            matrix = state.matrix
                        )
                        val (noClear, clearing, cleared) = updatedBricks
                        val newState = state.copy(
                            spirit = state.spiritNext,
                            spiritReserve = (state.spiritReserve - state.spiritNext).takeIf { it.isNotEmpty() }
                                ?: generateSpiritReverse(state.matrix),
                            score = state.score + calculateScore(clearedLines) +
                                    if (state.spirit != Empty) ScoreEverySpirit else 0,
                            line = state.line + clearedLines
                        )
                        if (clearedLines != 0) {
                            SoundUtil.play(state.isMute, SoundType.Clean)
                            state.copy(
                                gameStatus = GameStatus.LineClearing
                            ).also {
                                launch {
                                    repeat(5) {
                                        emit(
                                            state.copy(
                                                gameStatus = GameStatus.LineClearing,
                                                spirit = Empty,
                                                bricks = if (it % 2 == 0) noClear else clearing
                                            )
                                        )
                                        delay(100)
                                    }
                                    emit(
                                        newState.copy(
                                            bricks = cleared,
                                            gameStatus = GameStatus.Running
                                        )
                                    )
                                }
                            }
                        } else {
                            newState.copy(bricks = noClear)
                        }.also { finalState ->
                            if (finalState.currentHindrance != null) {
                                hindranceBlockCount++
                                // Log the block count
                                println("Hindrance block count: $hindranceBlockCount")
                            }
                        }
                    }
                    Action.Mute -> state.copy(isMute = !state.isMute)
                    Action.GameOver -> state.copy(gameStatus = GameStatus.GameOver)
                })
            }
        }
    }


    private suspend fun clearScreen(state: ViewState): ViewState {
        SoundUtil.play(state.isMute, SoundType.Start)
        val xRange = 0 until state.matrix.first
        var newState = state

        (state.matrix.second downTo 0).forEach { y ->
            emit(
                state.copy(
                    gameStatus = GameStatus.ScreenClearing,
                    bricks = state.bricks + Brick.of(
                        xRange, y until state.matrix.second
                    )
                )
            )
            delay(50)
        }
        (0..state.matrix.second).forEach { y ->
            emit(
                state.copy(
                    gameStatus = GameStatus.ScreenClearing,
                    bricks = Brick.of(xRange, y until state.matrix.second),
                    spirit = Empty
                ).also { newState = it }
            )
            delay(50)
        }
        return newState
    }

    private fun emit(state: ViewState) {
        _viewState.value = state
    }

    private fun updateBricks(
        curBricks: List<Brick>,
        spirit: Spirit,
        matrix: Pair<Int, Int>
    ): Pair<Triple<List<Brick>, List<Brick>, List<Brick>>, Int> {
        val bricks = (curBricks + Brick.of(spirit))
        val map = mutableMapOf<Float, MutableSet<Float>>()
        bricks.forEach {
            map.getOrPut(it.location.y) {
                mutableSetOf()
            }.add(it.location.x)
        }
        var clearing = bricks
        var cleared = bricks
        val clearLines = map.entries.sortedBy { it.key }
            .filter { it.value.size == matrix.first }.map { it.key }
            .onEach { line ->
                // clear line
                clearing = clearing.filter { it.location.y != line }
                // clear line and then offset brick
                cleared = cleared.filter { it.location.y != line }
                    .map { if (it.location.y < line) it.offsetBy(0 to 1) else it }
            }

        return Triple(bricks, clearing, cleared) to clearLines.size
    }

    private fun isGameOver(state: ViewState): Boolean {
        val gameOverLine = state.matrix.second - 1
        state.gameStatus = GameStatus.GameOver
        return state.bricks.any { it.location.y <= gameOverLine }
    }

    data class ViewState(
        val bricks: List<Brick> = emptyList(),
        val spirit: Spirit = Empty,
        val spiritReserve: List<Spirit> = emptyList(),
        val matrix: Pair<Int, Int> = MatrixWidth to MatrixHeight,
        var gameStatus: GameStatus = GameStatus.Onboard,
        val score: Int = 0,
        val line: Int = 0,
        val isMute: Boolean = false,
        val initialSpawn: Boolean = true,
        val currentHindrance: Hindrance? = null,
        val hindranceBlocksRemaining: Int = 0,
        val showHindranceAlert: Boolean = false
    ) {
        val level: Int
            get() = min(10, 1 + line / 20)

        val spiritNext: Spirit
            get() = spiritReserve.firstOrNull() ?: Empty

        val isPaused
            get() = gameStatus == GameStatus.Paused

        val isRunning
            get() = gameStatus == GameStatus.Running
    }
}

sealed interface Action {
    data class Move(val direction: Direction) : Action
    object Reset : Action
    object Pause : Action
    object Resume : Action
    object Rotate : Action
    object Drop : Action
    object GameTick : Action
    object Mute : Action
    object GameOver: Action
}

enum class GameStatus {
    Onboard,
    Running,
    LineClearing,
    Paused,
    ScreenClearing,
    GameOver
}

private const val MatrixWidth = 12
private const val MatrixHeight = 24
