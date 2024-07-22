package com.iccas.zen.presentation.chatBot.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.TextField
import com.iccas.zen.ui.theme.Brown40

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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(Color.White)
                    .border(1.dp, Color.Black, RoundedCornerShape(20))
                    .padding(horizontal = 10.dp, vertical = 13.dp)
            ) {
                Text(
                    text = message,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            TextField(
                value = inputState.value,
                onValueChange = { inputState.value = it },
                placeholder = { Text(question, color = Color.Gray)  },
                textStyle = TextStyle(color = Color.Black, fontSize = 17.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15)),
                colors = TextFieldDefaults.textFieldColors(
                    placeholderColor = Color.Gray,
                    textColor = Color.Black,
                    backgroundColor = Brown40.copy(alpha = 0.3f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}
