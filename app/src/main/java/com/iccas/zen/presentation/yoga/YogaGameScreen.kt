package com.iccas.zen.presentation.yoga

import android.annotation.SuppressLint
import android.util.Size
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackground
import com.iccas.zen.presentation.components.UserViewModel
import com.iccas.zen.presentation.components.PlayControlButton
import com.iccas.zen.ui.theme.Red50
import com.iccas.zen.utils.MusicManager
import kotlinx.coroutines.delay

@Composable
fun YogaGameScreen(
    initialPoseIndex: Int,
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentPoseIndex = remember { mutableIntStateOf(initialPoseIndex) }
    val currentPose = yogaPoses[currentPoseIndex.intValue]
    val moveNetModel = remember { MoveNetModel(context) }

    var showDialog by remember { mutableStateOf(false) }
    var leafCount by remember { mutableIntStateOf(initialPoseIndex) }
    var isTimerPaused by remember { mutableStateOf(true) }
    var isInstructionShowing by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        MusicManager.stopMainMusic()
        MusicManager.initializeYogaMusic(context)
        MusicManager.playYogaMusic()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            title = {},
            text = { ResultContent(onDismiss = {
                userViewModel.addLeaf(50)
                navController.navigate("game_select")
                                               },
                leafCount = leafCount) }
        )
    } else {
        BasicBackground {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zen_brown_logo),
                    contentDescription = null,
                    modifier = Modifier.width(65.dp),
                    contentScale = ContentScale.Crop
                )

                PlayControlButton(
                    modifier = Modifier
                        .size(50.dp, 40.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(50)),
                    onClick = {
                        MusicManager.stopMusic()
                        showDialog = true },
                    background = Red50,
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "quit game",
                    iconColor = Color.Black,
                    iconSize = 25.dp
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isInstructionShowing) {
                    YogaPoseInstruction(
                        poseIndex = currentPoseIndex.intValue,
                        onInstructionComplete = {
                            isInstructionShowing = false
                            isTimerPaused = true
                        }
                    )
                } else {
                    TimerText(
                        totalTime = currentPose.durationSeconds,
                        fontSize = 36,
                        color = Color.Black,
                        onTimeEnd = {
                            if (currentPoseIndex.intValue < yogaPoses.size - 1) {
                                currentPoseIndex.intValue += 1
                                leafCount += 1
                                isInstructionShowing = true
                            } else {
                                leafCount += 1
                                showDialog = true
                            }
                        },
                        isPaused = isTimerPaused
                    )
                    Text(
                        text = "${currentPose.name} (${currentPose.durationSeconds} sec)",
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = currentPose.poseImgId),
                        contentDescription = "Yoga Pose",
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                            .padding(bottom = 5.dp)
                    )

                    CameraPreview(
                        model = moveNetModel,
                        onPoseMatch = { isMatched ->
                            isTimerPaused = !isMatched
                        },
                        currentPoseName = currentPose.name,
                        isTimerPaused = isTimerPaused,
                        cameraPreviewSize = 300.dp
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Row() {
                        Spacer(modifier = Modifier.width(10.dp))
                        repeat(5) { index ->
                            Image(
                                painter = painterResource(
                                    id = if (index < leafCount) {
                                        R.drawable.yoga_leaf_filled
                                    } else {
                                        R.drawable.yoga_leaf_unfilled
                                    }
                                ),
                                contentDescription = "Leaf Icon",
                                modifier = Modifier.size(35.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun YogaPoseInstruction(
    poseIndex: Int,
    onInstructionComplete: () -> Unit
) {
    val currentPose = yogaPoses[poseIndex]

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Follow the\nnext move",
            fontSize = 35.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )

        Image(
            painter = painterResource(id = currentPose.poseImgId),
            contentDescription = "yoga_pose_img",
            modifier = Modifier.size(400.dp)
        )

        Text(
            text = "${currentPose.name} (${currentPose.durationSeconds} sec)",
            fontSize = 30.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }

    LaunchedEffect(Unit) {
        delay(4000)
        onInstructionComplete()
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TimerText(
    totalTime: Int,
    fontSize: Int,
    color: Color,
    onTimeEnd: () -> Unit,
    isPaused: Boolean
) {
    var timeLeft by remember { mutableIntStateOf(totalTime) }

    LaunchedEffect(key1 = timeLeft, key2 = isPaused) {
        if (timeLeft > 0 && !isPaused) {
            delay(1000L)
            timeLeft -= 1
        } else if (timeLeft == 0) {
            onTimeEnd()
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeString = String.format("%d:%02d", minutes, seconds)

    Text(
        text = timeString,
        fontSize = fontSize.sp,
        color = color
    )
}

@Composable
fun CameraPreview(
    model: MoveNetModel,
    onPoseMatch: (Boolean) -> Unit,
    currentPoseName: String,
    isTimerPaused: Boolean,
    cameraPreviewSize: Dp
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val coordinates = remember { model.analyzer.coordinates }
    val poseClass = remember { mutableStateOf("unknown") }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        val imageAnalyzer = ImageAnalysis.Builder()
            .setTargetResolution(Size(256, 256))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(ContextCompat.getMainExecutor(context), model.analyzer)
            }

        model.analyzer.onPoseClassified = { classResult ->
            poseClass.value = classResult
            onPoseMatch(classResult == currentPoseName)
        }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageAnalyzer
            )
        } catch (exc: Exception) {
            // TODO: 에러 처리
        }
    }
    Box(
        modifier = Modifier
            .size(cameraPreviewSize)
            .border(4.dp, if (isTimerPaused) Color.Red else Color.Green)
    ) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.camera_preview, null).apply {
                    findViewById<LinearLayout>(R.id.camera_container).addView(previewView)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Canvas(modifier = Modifier.fillMaxWidth()) {
            val previewWidth = previewView.width.toFloat()
            val previewHeight = previewView.height.toFloat()
            drawCoordinates(coordinates, previewWidth, previewHeight)
        }

        if (poseClass.value != "unknown") {
            Text(
                text = "Predicted Pose: ${poseClass.value}",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

fun DrawScope.drawCoordinates(
    coordinates: List<Pair<Float, Float>>,
    previewWidth: Float,
    previewHeight: Float
) {
    if (coordinates.size < 17) return

    val scaledCoordinates = coordinates.map { (x, y) ->
        Pair(x * previewWidth, y * previewHeight)
    }

    for ((start, end) in bodyConnections) {
        if (start < scaledCoordinates.size && end < scaledCoordinates.size) {
            val startPoint = scaledCoordinates[start]
            val endPoint = scaledCoordinates[end]
            if (startPoint.first >= 0 && startPoint.second >= 0 && endPoint.first >= 0 && endPoint.second >= 0) {
                drawLine(
                    color = Color.Green,
                    start = Offset(startPoint.first, startPoint.second),
                    end = Offset(endPoint.first, endPoint.second),
                    strokeWidth = 4.dp.toPx()
                )
            }
        }
    }

    for ((x, y) in scaledCoordinates) {
        if (x >= 0 && y >= 0) {
            drawCircle(
                color = Color.Red,
                radius = 5.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}

private val bodyConnections = listOf(
    Pair(0, 1), Pair(0, 2),
    Pair(1, 3), Pair(2, 4),
    Pair(0, 5), Pair(0, 6),
    Pair(5, 7), Pair(7, 9),
    Pair(6, 8), Pair(8, 10),
    Pair(5, 11), Pair(6, 12),
    Pair(11, 13), Pair(13, 15),
    Pair(12, 14), Pair(14, 16)
)
