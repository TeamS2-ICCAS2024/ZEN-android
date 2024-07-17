package com.iccas.zen.presentation.chatBot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import kotlinx.coroutines.launch
import com.iccas.zen.R
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iccas.zen.presentation.chatBot.components.TopBar
import com.iccas.zen.ui.theme.Blue90

@Composable
fun ChatScreen(navController: NavHostController, emojiResId: Int, basicPrompt: String, viewModel: ChatViewModel = viewModel()) {
    var userInput by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()


    BasicBackgroundWithLogo {
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val view = LocalView.current
        var imeHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        DisposableEffect(view) {
            val listener = ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())

                imeHeight = if (imeVisible) {
                    with(density) { insets.getInsets(WindowInsetsCompat.Type.ime()).bottom.toDp() }
                } else {
                    0.dp
                }

                insets
            }
            onDispose { ViewCompat.setOnApplyWindowInsetsListener(view, null) }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(navController)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                    reverseLayout = true,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(messages.asReversed()) { message ->
                        MessageItem(message, emojiResId)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type your message...") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
                )

                IconButton(
                    onClick = {
                        if (userInput.isNotBlank()) {
                            coroutineScope.launch {
                                viewModel.sendMessage(userInput, basicPrompt)
                                userInput = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .background(Color(0xFF0087B3), CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chat_arrow_up),
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
                painter = painterResource(id = R.drawable.chat_bao),
                contentDescription = "BAO",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) Blue90 else Color.White,
                    shape = RoundedCornerShape(12.dp) // 둥근 모서리를 위해 RoundedCornerShape 사용
                )
                .padding(16.dp)
        ) {
            Text(
                text = message.text, // 사용자에게는 입력한 내용만 표시
                color = if (message.isUser) Color.Black else Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        if (message.isUser) {
            Image(
                painter = painterResource(id = emojiResId),
                contentDescription = "User",
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}
