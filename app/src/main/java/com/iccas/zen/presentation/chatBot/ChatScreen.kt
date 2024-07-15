package com.iccas.zen.presentation.chatBot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import kotlinx.coroutines.launch
import com.iccas.zen.R
import androidx.navigation.NavHostController

@Composable
fun ChatScreen(navController: NavHostController, emojiResId: Int, viewModel: ChatViewModel = viewModel()) {
    BasicBackgroundWithLogo {
        val messages by viewModel.messages.collectAsState()
        var inputText by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(messages) { message ->
                    MessageItem(message, emojiResId)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type your message...") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            coroutineScope.launch {
                                viewModel.sendMessage(inputText)
                                inputText = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .background(Color(0xFF0087B3), CircleShape) // 하늘색 배경과 원형 모양
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_up), // 해당 아이콘 리소스를 사용
                        contentDescription = "Send",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
    }

@Composable
fun MessageItem(message: Message, emojiResId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!message.isUser) {
            Image(
                painter = painterResource(id = R.drawable.bao),
                contentDescription = "BAO",
                modifier = Modifier.size(50.dp).padding(end = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(
                    if (message.isUser) Color(135, 206, 235) else Color.White,
                    MaterialTheme.shapes.medium
                )
                .padding(16.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) Color.Black else Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        if (message.isUser) {
            Image(
                painter = painterResource(id = emojiResId), // 사용자 이모지 리소스
                contentDescription = "User",
                modifier = Modifier.size(50.dp).padding(start = 8.dp)
            )
        }
    }
}
