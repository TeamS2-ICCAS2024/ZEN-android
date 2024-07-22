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
            EmotionButtonsRow(
                navController,
                whatHappenedState.value,
                howFeelingState.value,
                whyFeelingState.value,
                characterDescription
            )
        }
    }
}

@Composable
fun EmotionButtonsRow(
    navController: NavHostController,
    whatHappened: String,
    howFeeling: String,
    whyFeeling: String,
    characterDescription: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        val emotions = listOf(
            Triple(R.drawable.chat_happy, "Happy", "You feel happy because $whatHappened, $howFeeling, and $whyFeeling."),
            Triple(R.drawable.chat_soso, "Soso", "You feel so-so because $whatHappened, $howFeeling, and $whyFeeling."),
            Triple(R.drawable.chat_sad, "Sad", "You feel sad because $whatHappened, $howFeeling, and $whyFeeling."),
            Triple(R.drawable.chat_angry, "Angry", "You feel angry because $whatHappened, $howFeeling, and $whyFeeling.")
        )

        emotions.forEach { (imageResId, label, promptPrefix) ->
            EmotionButton(
                navController = navController,
                imageResId = imageResId,
                label = label,
                size = 50.dp,
                promptPrefix = promptPrefix,
                characterDescription = characterDescription
            )
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
                val encodedPrompt = promptPrefix.encode()
                Log.d("EmotionButton", "Navigating to chat_screen with prompt: $encodedPrompt")
                navController.navigate("chat_screen/$imageResId?prompt=$encodedPrompt&characterDescription=$characterDescription")
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
