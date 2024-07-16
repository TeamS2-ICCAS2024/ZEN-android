package com.iccas.zen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iccas.zen.R
import com.iccas.zen.presentation.chatBot.components.EmotionHeader
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.chatBot.components.TopBar
import com.iccas.zen.ui.theme.Blue80

@Composable
fun SelectEmotionScreen(navController: NavHostController) {
    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(navController)

            EmotionHeader(
                imageResId = R.drawable.chat_bao,
                //imageDescription = "Bao",
                imageSize = 60.dp,
                title = "BAO",
                message = "What happened today?"
            )

            EmotionHeader(
                imageResId = R.drawable.chat_bao,
                //imageDescription = "Bao",
                imageSize = 60.dp,
                title = "BAO",
                message = "How are you feeling today?"
            )


            EmotionHeader(
                imageResId = R.drawable.chat_bao,
                imageDescription = "Bao",
                imageSize = 60.dp,
                title = "BAO",
                message = "Why do you feel that way?"
            )



            // 버튼들을 화면에 꽉 차게 배치
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                EmotionButton(navController, R.drawable.chat_happy, "Happy", 65.dp)
                EmotionButton(navController, R.drawable.chat_soso, "Soso", 83.dp)
                EmotionButton(navController, R.drawable.chat_sad, "Sad", 67.dp)
                EmotionButton(navController, R.drawable.chat_angry, "Angry", 65.dp)
            }
        }
    }
}

@Composable
fun EmotionButton(navController: NavHostController, imageResId: Int, label: String, size: Dp) {
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
                .size(size) // 크기를 줄여서 한 줄에 4개가 잘 들어가도록 설정
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
