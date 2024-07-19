package com.iccas.zen

import android.util.Log
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.iccas.zen.presentation.chatBot.ChatViewModel
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.chatBot.components.TopBar
import com.iccas.zen.presentation.chatBot.components.EmotionHeader

@Composable
fun SelectEmotionScreen(navController: NavHostController, characterImageRes: Int, characterDescription: String) {
    val whatHappenedState = remember { mutableStateOf("") }
    val howFeelingState = remember { mutableStateOf("") }
    val whyFeelingState = remember { mutableStateOf("") }

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(navController)

            // 감정 헤더 추가
            EmotionHeader(
                imageResId = characterImageRes,
                imageDescription = characterDescription,
                imageSize = 60.dp,
                title = characterDescription,
                message = "What happened today?",
                question = "Can you describe what happened?",
                inputState = whatHappenedState
            )

            EmotionHeader(
                imageResId = characterImageRes,
                imageDescription = characterDescription,
                imageSize = 60.dp,
                title = characterDescription,
                message = "How are you feeling today?",
                question = "How are you feeling?",
                inputState = howFeelingState
            )

            EmotionHeader(
                imageResId = characterImageRes,
                imageDescription = characterDescription,
                imageSize = 60.dp,
                title = characterDescription,
                message = "Why do you feel that way?",
                question = "Can you explain why you feel that way?",
                inputState = whyFeelingState
            )

            // 버튼들을 화면에 꽉 차게 배치
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                EmotionButton(
                    navController,
                    R.drawable.chat_happy,
                    "Happy",
                    70.dp,
                    "You feel happy because ${whatHappenedState.value}, ${howFeelingState.value}, and ${whyFeelingState.value}.",
                    characterDescription
                )
                EmotionButton(
                    navController,
                    R.drawable.chat_soso,
                    "Soso",
                    70.dp,
                    "You feel so-so because ${whatHappenedState.value}, ${howFeelingState.value}, and ${whyFeelingState.value}.",
                    characterDescription
                )
                EmotionButton(
                    navController,
                    R.drawable.chat_sad,
                    "Sad",
                    70.dp,
                    "You feel sad because ${whatHappenedState.value}, ${howFeelingState.value}, and ${whyFeelingState.value}.",
                    characterDescription
                )
                EmotionButton(
                    navController,
                    R.drawable.chat_angry,
                    "Angry",
                    70.dp,
                    "You feel angry because ${whatHappenedState.value}, ${howFeelingState.value}, and ${whyFeelingState.value}.",
                    characterDescription
                )
            }
        }
    }
}

@Composable
fun EmotionButton(
    navController: NavHostController,
    imageResId: Int,
    label: String,
    size: Dp,
    promptPrefix: String,
    characterDescription: String
) {
    val viewModel: ChatViewModel = viewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                Log.d("EmotionButton", "Prompt Prefix: $promptPrefix")
                viewModel.postEmotionDiary(promptPrefix, characterDescription, label)
                navController.navigate("chat_screen/$imageResId?prompt=${promptPrefix.encode()}&characterDescription=$characterDescription")
            }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = Modifier
                .size(size)
                .background(Color.Transparent, CircleShape)
        )
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

fun String.encode(): String = java.net.URLEncoder.encode(this, "UTF-8")
