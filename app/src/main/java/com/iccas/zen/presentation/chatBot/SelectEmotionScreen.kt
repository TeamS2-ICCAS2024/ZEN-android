package com.iccas.zen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun SelectEmotionScreen(navController: NavHostController) {
    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 상단 이미지와 텍스트를 나란히 배치
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bao), // 추가한 이미지 리소스 사용
                        contentDescription = "Bao",
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = "BAO",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "How are you feeling today?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp))

            // 버튼들을 화면에 꽉 차게 배치
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                EmotionButton(navController, R.drawable.happy, "Happy")
                EmotionButton(navController, R.drawable.soso, "Soso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                EmotionButton(navController, R.drawable.sad, "Sad")
                EmotionButton(navController, R.drawable.angry, "Angry")
            }
        }
    }
}

@Composable
fun EmotionButton(navController: NavHostController, imageResId: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate("chat_screen/$imageResId")
            }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = Modifier
                .size(100.dp) // 크기를 키워서 꽉 차게 보이도록 설정
                .background(Color.Transparent, CircleShape) // 배경색 제거
        )
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
