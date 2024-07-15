package com.iccas.zen.presentation.tetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.presentation.tetris.logic.Direction
import com.iccas.zen.presentation.tetris.tetrisComponents.GameButton
import com.iccas.zen.presentation.components.BasicBackground

@Composable
fun GameBody(
    clickable: Clickable = combinedClickable(),
    measureHeartViewModel: MeasureHeartViewModel,
    lives: Int,
    screen: @Composable () -> Unit,
) {
    val currentHeartRate = measureHeartViewModel.receivedData.collectAsState().value
    val isMute = remember { mutableStateOf(true) }

    // Game Button
    val ButtonText = @Composable { modifier: Modifier,
                                   text: String ->
        Text(
            text, modifier = modifier,
            color = Color.White.copy(0.9f),
            fontSize = 17.sp
        )
    }
    BasicBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zen_brown_logo),
                    contentDescription = null,
                    modifier = Modifier.width(65.dp),
                    contentScale = ContentScale.Crop
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = if (isMute.value) R.drawable.tetris_game_volume_on else R.drawable.tetris_game_volume_off),
                        contentDescription = "Music icon",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                isMute.value = !isMute.value
                                clickable.onMute()
                            },
                        colorFilter = ColorFilter.tint(Color.Black)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Image(
                        painter = painterResource(id = R.drawable.heart_rate_icon),
                        contentDescription = "BPM icon",
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = currentHeartRate,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    val heartIcon = if (index < (lives ?: 0)) {
                        R.drawable.tetris_game_heart_filled
                    } else {
                        R.drawable.tetris_game_heart_unfilled
                    }
                    Image(
                        painter = painterResource(id = heartIcon),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(360.dp, 440.dp)
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawScreenBorder(
                        Offset(0f, 0f),
                        Offset(size.width, 0f),
                        Offset(0f, size.height),
                        Offset(size.width, size.height)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    screen()
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
            ) {
                Spacer(modifier = Modifier.height(5.dp))

                Row {
                    GameButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 10.dp, end = 10.dp),
                        onClick = { clickable.onRestart() },
                        size = StopButtonSize / 4
                    ) {
                        ButtonText(it, "replay")
                    }
                    GameButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 15.dp, end = 10.dp),
                        onClick = { clickable.onGameOver() },
                        size = StopButtonSize / 4
                    ) {
                        ButtonText(it, "quit")
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // DIRECTION BTN
                Box(
                    modifier = Modifier.size(DirectionButtonSize*3)
                ) {
                    GameButton(
                        Modifier.align(Alignment.TopCenter),
                        onClick = { clickable.onMove(Direction.Up) },
                        autoInvokeWhenPressed = false,
                        size = DirectionButtonSize
                    ) {
                        ButtonText(it, stringResource(id = R.string.button_up))
                    }
                    GameButton(
                        Modifier.align(Alignment.CenterStart),
                        onClick = { clickable.onMove(Direction.Left) },
                        autoInvokeWhenPressed = true,
                        size = DirectionButtonSize
                    ) {
                        ButtonText(it, stringResource(id = R.string.button_left))
                    }
                    GameButton(
                        Modifier.align(Alignment.CenterEnd),
                        onClick = { clickable.onMove(Direction.Right) },
                        autoInvokeWhenPressed = true,
                        size = DirectionButtonSize
                    ) {
                        ButtonText(it, stringResource(id = R.string.button_right))
                    }
                    GameButton(
                        Modifier.align(Alignment.BottomCenter),
                        onClick = { clickable.onMove(Direction.Down) },
                        autoInvokeWhenPressed = true,
                        size = DirectionButtonSize
                    ) {
                        ButtonText(it, stringResource(id = R.string.button_down))
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))

                // ROTATE BTN
                Box(
                    modifier = Modifier.size(DirectionButtonSize*3)
                ) {
                    GameButton(
                        Modifier.align(Alignment.CenterEnd),
                        onClick = { clickable.onRotate() },
                        autoInvokeWhenPressed = false,
                        size = RotateButtonSize
                    ) {
                        ButtonText(it, stringResource(id = R.string.button_rotate))
                    }
                }
            }
        }
    }
}

fun DrawScope.drawScreenBorder(
    topLef: Offset,
    topRight: Offset,
    bottomLeft: Offset,
    bottomRight: Offset
) {
    var path = Path().apply {
        moveTo(topLef.x, topLef.y)
        lineTo(topRight.x, topRight.y)
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            topLef.y + topRight.x / 2 + topLef.x / 2
        )
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            bottomLeft.y - topRight.x / 2 + topLef.x / 2
        )
        lineTo(bottomLeft.x, bottomLeft.y)
        close()
    }
    drawPath(path, Color.Black.copy(0.5f))

    path = Path().apply {
        moveTo(bottomRight.x, bottomRight.y)
        lineTo(bottomLeft.x, bottomLeft.y)
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            bottomLeft.y - topRight.x / 2 + topLef.x / 2
        )
        lineTo(
            topRight.x / 2 + topLef.x / 2,
            topLef.y + topRight.x / 2 + topLef.x / 2
        )
        lineTo(topRight.x, topRight.y)
        close()
    }

    drawPath(path, Color.White.copy(0.5f))
}

data class Clickable constructor(
    val onMove: (Direction) -> Unit,
    val onRotate: () -> Unit,
    val onRestart: () -> Unit,
    val onPause: () -> Unit,
    val onMute: () -> Unit,
    val onGameOver: () -> Unit
)

fun combinedClickable(
    onMove: (Direction) -> Unit = {},
    onRotate: () -> Unit = {},
    onRestart: () -> Unit = {},
    onPause: () -> Unit = {},
    onMute: () -> Unit = {},
    onGameOver: () -> Unit = {}
) = Clickable(onMove, onRotate, onRestart, onPause, onMute, onGameOver)

val StopButtonSize = 100.dp
val DirectionButtonSize = 35.dp
val RotateButtonSize = 85.dp
