package com.iccas.zen.presentation.tetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel
import com.iccas.zen.presentation.tetris.logic.Direction
import com.iccas.zen.ui.theme.Blue80
import com.iccas.zen.ui.theme.BodyColor
import com.iccas.zen.ui.theme.ScreenBackground

@Composable
fun GameBody(
    clickable: Clickable = combinedClickable(),
    measureHeartViewModel: MeasureHeartViewModel,
    lives: Int,
    screen: @Composable () -> Unit,
) {
    val currentHeartRate = measureHeartViewModel.receivedData.collectAsState().value

    // Game Button
    val ButtonText = @Composable { modifier: Modifier,
                                   text: String ->
        Text(
            text, modifier = modifier,
            color = Color.White.copy(0.9f),
            fontSize = 17.sp
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .background(BodyColor, RoundedCornerShape(10.dp))
            .padding(top = 25.dp)
            .padding(bottom = 10.dp)
    ) {
        // Header
        Box(Modifier.align(Alignment.CenterHorizontally)) {
            Box(
                Modifier
                    .width(350.dp)
                    .height(35.dp)
                    .align(Alignment.TopStart)
                    .background(BodyColor)
            ) {
                Text(
                    text = "\t\tZEN",
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 16.dp)
                )

                GameButton(
                    size = 40.dp,
                    onClick = { clickable.onMute() },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tetris_baseline_music),
                        contentDescription = "Center-aligned image",
                        modifier = Modifier.size(40.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tetris_bpm),
                        contentDescription = "BPM icon",
                        modifier = Modifier
                            .size(40.dp)
                    )

                    Text(
                        text = currentHeartRate,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 48.dp, top = 8.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
        ) {
            repeat(lives) {
                Image(
                    painter = painterResource(id = R.drawable.tetris_bpm),
                    contentDescription = "Heart",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(1.dp))

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .size(360.dp, 440.dp)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp)
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
                    .background(ScreenBackground)
                    .background(Blue80)
            ) {
                screen()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                GameButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp, end = 20.dp),
                    onClick = { clickable.onRestart() },
                    size = StopButtonSize / 4
                ) {
                    ButtonText(it, stringResource(id = R.string.button_down))
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .height(160.dp)
        ) {
            // DIRECTION BTN
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
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

            // ROTATE BTN
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
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
    val onMute: () -> Unit
)

fun combinedClickable(
    onMove: (Direction) -> Unit = {},
    onRotate: () -> Unit = {},
    onRestart: () -> Unit = {},
    onPause: () -> Unit = {},
    onMute: () -> Unit = {}
) = Clickable(onMove, onRotate, onRestart, onPause, onMute)

val StopButtonSize = 100.dp
val DirectionButtonSize = 50.dp
val RotateButtonSize = 100.dp
