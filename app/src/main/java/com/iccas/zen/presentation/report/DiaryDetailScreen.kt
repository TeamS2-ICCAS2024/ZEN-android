package com.iccas.zen.presentation.report

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.report.viewModel.ReportViewModel

@Composable
fun DiaryDetailScreen(
    navController: NavController,
    emotionDiaryId: Long,
    reportViewModel: ReportViewModel = viewModel()
) {
    val context = LocalContext.current
    val diaryDetail by reportViewModel.diaryDetail.collectAsState()

    LaunchedEffect(emotionDiaryId) {
        reportViewModel.getDiaryDetail(emotionDiaryId)
    }

    BasicBackgroundWithLogo {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                diaryDetail?.data?.let { detail ->
                    TextWithBackground("Analysis")

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            TextWithBackground("When")
                            Text(text = detail.whenDetail, fontSize = 18.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TextWithBackground("Emotion")
                            Image(
                                painter = painterResource(id = R.drawable.chat_angry), // 감정 이미지 리소스
                                contentDescription = "Emotion",
                                modifier = Modifier.size(32.dp)
                            )
                            Text(text = detail.emotion, fontSize = 18.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    TextWithBackground("Summary")
                    Text(
                        text = detail.summary,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextWithBackground("Solution")
                    Text(
                        text = detail.solution,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    TextWithBackground("Anger Frequency")

                    Spacer(modifier = Modifier.height(20.dp))
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "2 days ago", fontSize = 18.sp)
                            Text(text = "21 days ago", fontSize = 18.sp)
                            Text(text = "4 days ago", fontSize = 18.sp)
                            Text(text = "Now", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.chat_angry), // 감정 이미지 리소스
                                contentDescription = "Angry",
                                modifier = Modifier.size(32.dp)
                            )
                            DashedDivider()
                            Image(
                                painter = painterResource(id = R.drawable.chat_angry), // 감정 이미지 리소스
                                contentDescription = "Angry",
                                modifier = Modifier.size(32.dp)
                            )
                            DashedDivider()
                            Image(
                                painter = painterResource(id = R.drawable.chat_angry), // 감정 이미지 리소스
                                contentDescription = "Angry",
                                modifier = Modifier.size(32.dp)
                            )
                            DashedDivider()
                            Image(
                                painter = painterResource(id = R.drawable.chat_angry), // 감정 이미지 리소스
                                contentDescription = "Angry",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                } ?: run {
                    Text(
                        text = "Loading...",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TextWithBackground(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp) // Add padding to the text
    )
}

@Composable
fun DashedDivider() {
    Canvas(modifier = Modifier
        .width(24.dp)
        .height(1.dp)) {
        val paint = Paint().apply {
            color = Color.Gray
        }
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
        }
        drawPath(
            path = path,
            color = Color.Gray,
            style = Stroke(
                width = 4f,
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                    intervals = floatArrayOf(10f, 10f),
                    phase = 0f
                )
            )
        )
    }
}
