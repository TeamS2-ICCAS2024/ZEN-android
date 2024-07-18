package com.iccas.zen.presentation.tetris

import android.graphics.Paint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.CommonViewModel
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.presentation.tetris.logic.Action
import com.iccas.zen.presentation.tetris.logic.Brick
import com.iccas.zen.presentation.tetris.logic.Direction
import com.iccas.zen.presentation.tetris.logic.GameStatus
import com.iccas.zen.presentation.tetris.logic.GameViewModel
import com.iccas.zen.presentation.tetris.logic.NextMatrix
import com.iccas.zen.presentation.tetris.logic.Spirit
import com.iccas.zen.presentation.heart.HighHeartRateScreen
import com.iccas.zen.presentation.tetris.logic.Hindrance
import com.iccas.zen.presentation.tetris.tetrisComponents.LedClock
import com.iccas.zen.presentation.tetris.tetrisComponents.LedNumber
import com.iccas.zen.ui.theme.Blue80
import com.iccas.zen.ui.theme.BrickMatrix
import com.iccas.zen.ui.theme.BrickSpirit
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Brown50
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.min

@Composable
fun TetrisGameScreen(
    measureHeartViewModel: MeasureHeartViewModel,
    gameViewModel: GameViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    commonViewModel: CommonViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        gameViewModel.startHindranceTimer()
    }

    val viewState by gameViewModel.viewState
    val isHeartRateHigh by measureHeartViewModel.isHeartRateHigh.collectAsState()
    val lives = remember { mutableIntStateOf(5) }
    val heartRateExceeded = remember { mutableStateOf(false) }
    var showGameOverScreen by remember { mutableStateOf(false) }

    // Lifecycle observer for the viewModel
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = Unit) {
        val observer = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                gameViewModel.dispatch(Action.Resume)
            }

            override fun onPause(owner: LifecycleOwner) {
                gameViewModel.dispatch(Action.Pause)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Game tick effect
    LaunchedEffect(key1 = viewState.level) {
        gameViewModel.dispatch(Action.Reset)
        while (isActive) {
            delay(650L - 55 * (viewState.level - 1))
            gameViewModel.dispatch(Action.GameTick)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isHeartRateHigh) {
            if (!heartRateExceeded.value) {
                heartRateExceeded.value = true
                if (lives.value > 0) {
                    lives.value -= 1
                }
            }
            gameViewModel.dispatch(Action.Pause)
            HighHeartRateScreen()
        } else {
            if (heartRateExceeded.value) {
                heartRateExceeded.value = false
                gameViewModel.dispatch(Action.Resume)
            }
            if (viewState.gameStatus == GameStatus.GameOver) {
                showGameOverScreen = true
            }
            if (showGameOverScreen) {
                TetrisGameOverScreen(
                    level = viewState.level,
                    score = viewState.score,
                    lives = lives.value,
                    onReplay = {
                        showGameOverScreen = false
                        gameViewModel.dispatch(Action.Reset)
                        lives.value = 5
                    },
                    onExit = {
                        if (viewState.score >= 100) {
                            commonViewModel.addLeaf(50)
                        }
                        navController.navigate("game_select")
                    }
                )
            } else {
                GameBody(
                    combinedClickable(
                        onMove = { direction: Direction ->
                            if (direction == Direction.Up) gameViewModel.dispatch(Action.Drop)
                            else gameViewModel.dispatch(Action.Move(direction))
                        },
                        onRotate = { gameViewModel.dispatch(Action.Rotate) },
                        onRestart = {
                            gameViewModel.dispatch(Action.Reset)
                            lives.value = 5

                        },
                        onPause = {
                            if (viewState.isRunning) {
                                gameViewModel.dispatch(Action.Pause)
                            } else {
                                gameViewModel.dispatch(Action.Resume)
                            }
                        },
                        onMute = { gameViewModel.dispatch(Action.Mute) },
                        onGameOver = { gameViewModel.dispatch(Action.GameOver) }
                    ),
                    measureHeartViewModel = measureHeartViewModel,
                    lives = lives.intValue
                ) {
                    Box(
                        modifier
                            .background(Color.Black)
                            .padding(1.dp)
                            .background(Brown50)
                            .padding(10.dp)
                    ) {
                        val animateValue by rememberInfiniteTransition(label = "").animateFloat(
                            initialValue = 0f, targetValue = 0.7f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1500),
                                repeatMode = RepeatMode.Reverse,
                            ),
                            label = "",
                        )

                        Canvas(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val brickSize = min(
                                size.width / viewState.matrix.first,
                                size.height / viewState.matrix.second
                            )

                            drawMatrix(brickSize, viewState.matrix)
                            drawMatrixBorder(brickSize, viewState.matrix)
                            drawBricks(viewState.bricks, brickSize, viewState.matrix)
                            drawSpirit(viewState.spirit, brickSize, viewState.matrix)
                            drawText(viewState.gameStatus, brickSize, viewState.matrix, 0.7f)
                        }

                        GameScoreboard(
                            spirit = run {
                                if (viewState.spirit == Spirit.Empty) Spirit.Empty
                                else viewState.spiritNext.rotate()
                            },
                            score = viewState.score,
                            line = viewState.line,
                            level = viewState.level,
                            isMute = viewState.isMute,
                            isPaused = viewState.isPaused
                        )
                    }
                }
                if (viewState.showHindranceAlert) {
                    HindranceAlert(
                        hindrance = viewState.currentHindrance,
                        onDismiss = {
                            gameViewModel.hideHindranceAlert()
                            gameViewModel.dispatch(Action.Resume)
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun HindranceAlert(hindrance: Hindrance?, onDismiss: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(4000)
        onDismiss()
    }
    if(hindrance!=null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val imageResource = when (hindrance) {
                    Hindrance.RandomDirection -> R.drawable.random3
                    Hindrance.DisableRotation -> R.drawable.disable_rotate3
                    Hindrance.ReverseControl -> R.drawable.reverse_control4
                }
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp)) // 이미지와 텍스트 사이의 공간
                Text(
                    text = when (hindrance) {
                        Hindrance.RandomDirection -> "Now, You can't move as you want."
                        Hindrance.DisableRotation -> "Now, You can't use the rotate button."
                        Hindrance.ReverseControl -> "The left and right are reversed."
                    },
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(
                            Color.Red,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun GameScoreboard(
    modifier: Modifier = Modifier,
    brickSize: Float = 35f,
    spirit: Spirit,
    score: Int = 0,
    line: Int = 0,
    level: Int = 1,
    isMute: Boolean = false,
    isPaused: Boolean = false
) {
    Row(modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(0.55f))
        val textSize = 12.sp
        val margin = 10.dp

        Column(
            Modifier
                .fillMaxHeight()
                .weight(0.15f)
                .background(Brown40)
        ) {
            Text("Score", fontSize = textSize)
            LedNumber(Modifier.fillMaxWidth(), score, 6)

            Spacer(modifier = Modifier.height(margin))

            Text("Lines", fontSize = textSize)
            LedNumber(Modifier.fillMaxWidth(), line, 6)

            Spacer(modifier = Modifier.height(margin))

            Text("Level", fontSize = textSize)
            LedNumber(Modifier.fillMaxWidth(), level, 1)

            Spacer(modifier = Modifier.height(margin))

            Text("Next", fontSize = textSize)
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                drawMatrix(brickSize, NextMatrix)
                drawSpirit(
                    spirit.adjustOffset(NextMatrix),
                    brickSize = brickSize, NextMatrix
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Row {
                Image(
                    modifier = Modifier.width(16.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_pause_24),
                    colorFilter = ColorFilter.tint(if (isPaused) BrickSpirit else BrickMatrix),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))

                LedClock()
            }
        }
    }
}

private fun DrawScope.drawText(
    gameStatus: GameStatus,
    brickSize: Float,
    matrix: Pair<Int, Int>,
    alpha: Float,
) {
    val center = Offset(
        brickSize * matrix.first / 2,
        brickSize * matrix.second / 2
    )
    val drawText = { text: String, size: Float ->
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text,
                center.x,
                center.y,
                Paint().apply {
                    color = Color.Black.copy(alpha = alpha).toArgb()
                    textSize = size
                    textAlign = Paint.Align.CENTER
                    style = Paint.Style.FILL_AND_STROKE
                    strokeWidth = size / 12
                }
            )
        }
    }
    if (gameStatus == GameStatus.GameOver) {
        drawText("GAME OVER", 60f)
    }
}

private fun DrawScope.drawMatrix(brickSize: Float, matrix: Pair<Int, Int>) {
    (0 until matrix.first).forEach { x ->
        (0 until matrix.second).forEach { y ->
            drawBrick(
                brickSize,
                Offset(x.toFloat(), y.toFloat()),
                BrickMatrix
            )
        }
    }
}

private fun DrawScope.drawMatrixBorder(brickSize: Float, matrix: Pair<Int, Int>) {
    val gap = matrix.first * brickSize * 0.05f
    drawRect(
        Color.Black,
        size = Size(
            matrix.first * brickSize + gap,
            matrix.second * brickSize + gap
        ),
        topLeft = Offset(
            -gap / 2,
            -gap / 2
        ),
        style = Stroke(1.dp.toPx())
    )
}

private fun DrawScope.drawBricks(brick: List<Brick>, brickSize: Float, matrix: Pair<Int, Int>) {
    clipRect(
        0f, 0f,
        matrix.first * brickSize,
        matrix.second * brickSize
    ) {
        brick.forEach {
            drawBrick(brickSize, it.location, BrickSpirit)
        }
    }
}

private fun DrawScope.drawSpirit(spirit: Spirit, brickSize: Float, matrix: Pair<Int, Int>) {
    clipRect(
        0f, 0f,
        matrix.first * brickSize,
        matrix.second * brickSize
    ) {
        spirit.location.forEach {
            drawBrick(
                brickSize,
                Offset(it.x, it.y),
                BrickSpirit
            )
        }
    }
}

private fun DrawScope.drawBrick(
    brickSize: Float,
    offset: Offset,
    color: Color
) {
    val actualLocation = Offset(
        offset.x * brickSize,
        offset.y * brickSize
    )

    val outerSize = brickSize * 0.8f
    val outerOffset = (brickSize - outerSize) / 2

    drawRect(
        color,
        topLeft = actualLocation + Offset(outerOffset, outerOffset),
        size = Size(outerSize, outerSize),
        style = Stroke(outerSize / 10)
    )

    val innerSize = brickSize * 0.5f
    val innerOffset = (brickSize - innerSize) / 2

    drawRect(
        color,
        actualLocation + Offset(innerOffset, innerOffset),
        size = Size(innerSize, innerSize)
    )
}

