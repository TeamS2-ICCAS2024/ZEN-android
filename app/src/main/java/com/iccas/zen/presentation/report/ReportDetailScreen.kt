package com.iccas.zen.presentation.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun ReportDetailScreen(navController: NavController, character: String, date: String) {
    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "analysis",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "When",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    )
                    Text(text = date, fontSize = 18.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "emotion",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    )
                    Image(
                        painter = painterResource(id = R.drawable.chat_angry), // 감정 이미지 리소스
                        contentDescription = "Emotion",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(text = "angry", fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "summary",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            )
            Text(
                text = "I got into a fight with my friend because he said something mean to me. And there is no communication from that friend.",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "solution",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            )
            Text(
                text = "If you say you are angry in a certain way, there is a certain way to tell.",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "anger frequency",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "2day", fontSize = 18.sp)
                    Text(text = "21day", fontSize = 18.sp)
                    Text(text = "4day", fontSize = 18.sp)
                    Text(text = "now", fontSize = 18.sp)
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
        }
    }
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
