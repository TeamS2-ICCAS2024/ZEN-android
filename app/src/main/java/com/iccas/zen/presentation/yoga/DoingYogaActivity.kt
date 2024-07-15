package com.cookandroid.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.cookandroid.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class DoingYogaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                setupContent()
            } else {
                // 권한이 거부된 경우 처리
            }
        }

        // 권한 요청
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setupContent()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun setupContent() {
        val poseIndex = intent.getIntExtra("poseIndex", 0)
        val currentPose = yogaPoses[poseIndex]
        val moveNetModel = MoveNetModel(this)

        setContent {
            MyApplicationTheme {
                var showDialog by remember { mutableStateOf(false) }
                var leafCount by remember { mutableStateOf(poseIndex) }
                var isTimerPaused by remember { mutableStateOf(false) }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        confirmButton = {},
                        title = { Text(text = "Result") },
                        text = { ResultContent(onDismiss = { navigateBack() }) }
                    )
                }

                Scaffold(
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BackButton(
                                    onClick = { navigateBack() }
                                )
                            }
                            TimerText(
                                totalTime = currentPose.durationSeconds,
                                fontSize = 36.sp,
                                color = Color.Black,
                                onTimeEnd = {
                                    if (poseIndex < yogaPoses.size - 1) {
                                        leafCount += 1
                                        navigateToShowActivity(poseIndex + 1)
                                    } else {
                                        showDialog = true
                                    }
                                },
                                isPaused = isTimerPaused // 타이머 멈춤 상태 반영
                            )
                            Text(
                                    text = "${currentPose.name} (${currentPose.durationSeconds} sec)",                                fontSize = 24.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = painterResource(id = currentPose.imageRes),
                                contentDescription = "Yoga Pose",
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                                    .padding(bottom = 16.dp)
                            )
                            CameraPreview(
                                modifier = Modifier
                                    .size(300.dp)
                                    .padding(bottom = 16.dp),
                                model = moveNetModel, // 모델 객체 전달
                                onPoseMatch = { isMatched ->
                                    isTimerPaused = !isMatched // 동작이 감지되지 않으면 타이머 멈춤, 감지되면 타이머 재개
                                },
                                currentPoseName = currentPose.name, // 현재 동작 이름 전달
                                isTimerPaused = isTimerPaused // 타이머 멈춤 상태 전달
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                items(leafCount) {
                                    LeafIcon()
                                }
                            }
                        }
                    }
                )
            }
        }
    }


    private fun navigateToShowActivity(poseIndex: Int) {
        val intent = Intent(this, ShowingActivity::class.java).apply {
            putExtra("poseIndex", poseIndex)
        }
        startActivity(intent)
        finish() // 현재 액티비티를 종료하여 뒤로 가기 시 돌아오지 않게 함
    }

    private fun navigateBack() {
        // MainActivity로 돌아가는 인텐트 설정
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun TimerText(
    totalTime: Int,
    fontSize: androidx.compose.ui.unit.TextUnit,
    color: Color,
    onTimeEnd: () -> Unit,
    isPaused: Boolean // 타이머 멈춤 상태 추가
) {
    var timeLeft by remember { mutableStateOf(totalTime) }

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
        fontSize = fontSize,
        color = color
    )
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    model: MoveNetModel,
    onPoseMatch: (Boolean) -> Unit,
    currentPoseName: String,
    isTimerPaused: Boolean
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
            onPoseMatch(classResult == currentPoseName) // 예측된 동작이 현재 동작과 일치하는지 확인하여 콜백 호출
        }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageAnalyzer
            )
        } catch (exc: Exception) {
            // 에러 처리
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight() // 화면 전체 높이를 채움
            .border(4.dp, if (isTimerPaused) Color.Red else Color.Green) // 타이머 상태에 따른 테두리 색상 설정
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize() // PreviewView의 크기도 화면 전체로 설정
        )
        Canvas(modifier = Modifier.matchParentSize()) {
            val previewWidth = previewView.width.toFloat()
            val previewHeight = previewView.height.toFloat()
            drawCoordinates(coordinates, previewWidth, previewHeight)
        }

        // 예측된 자세를 텍스트로 표시
        if (poseClass.value != "unknown") {
            Text(
                text = "Predicted Pose: ${poseClass.value}",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            )
        }
    }
}

fun DrawScope.drawCoordinates(coordinates: List<Pair<Float, Float>>, previewWidth: Float, previewHeight: Float) {
    if (coordinates.size < 17) return

    val scaledCoordinates = coordinates.map { (x, y) ->
        Pair(x * previewWidth, y * previewHeight)
    }

    // 선을 그립니다
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

    // 점을 그립니다
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

@Composable
fun LeafIcon() {
    Image(
        painter = painterResource(id = R.drawable.leaf), // 나뭇잎 이미지 파일
        contentDescription = "Leaf Icon",
        modifier = Modifier.size(70.dp) // 나뭇잎 이미지 크기 통일
    )
}

private val bodyConnections = listOf(
    Pair(0, 1), Pair(0, 2), // Nose to eyes
    Pair(1, 3), Pair(2, 4), // Eyes to ears
    Pair(0, 5), Pair(0, 6), // Nose to shoulders
    Pair(5, 7), Pair(7, 9), // Left arm
    Pair(6, 8), Pair(8, 10), // Right arm
    Pair(5, 11), Pair(6, 12), // Shoulders to hips
    Pair(11, 13), Pair(13, 15), // Left leg
    Pair(12, 14), Pair(14, 16) // Right leg
)
