package com.iccas.zen.presentation.chatBot.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.iccas.zen.ui.theme.Blue80

@Composable
fun EmotionHeader(
    imageResId: Int,
    imageDescription: String,
    imageSize: Dp,
    title: String,
    message: String,
    question: String,
    inputState: MutableState<String>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = imageDescription,
                    modifier = Modifier.size(imageSize)
                )
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Text(
                    text = message,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            androidx.compose.material.TextField(
                value = inputState.value,
                onValueChange = { inputState.value = it },
                placeholder = { Text(question) },
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Blue80,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}
