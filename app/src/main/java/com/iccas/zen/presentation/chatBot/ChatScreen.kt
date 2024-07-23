package com.iccas.zen.presentation.chatBot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iccas.zen.presentation.chatBot.chatViewModel.ChatViewModel
import com.iccas.zen.presentation.chatBot.components.TopBar
import com.iccas.zen.ui.theme.Blue90
import com.iccas.zen.ui.theme.Brown40

@Composable
fun ChatScreen(
    navController: NavHostController,
    emojiResId: Int,
    basicPrompt: String,
    characterDescription: String,
    viewModel: ChatViewModel = viewModel()
) {
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
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            TopBar(navController)

            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                    reverseLayout = true,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(messages.asReversed()) { message ->
                        MessageItem(message, emojiResId, characterDescription)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .border(1.dp, Brown40, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50)),
                    placeholder = { Text("Type your message...") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedLabelColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = Brown40,
                        unfocusedIndicatorColor = Color.Transparent // 포커스되지 않은 상태에서 밑줄을 숨김
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.chat_arrow_up),
                            contentDescription = "Send",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (userInput.isNotBlank()) {
                                        coroutineScope.launch {
                                            viewModel.sendMessage(userInput, basicPrompt)
                                            userInput = ""
                                        }
                                    }
                                },
                            tint = Color.Black
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, emojiResId: Int, characterDescription: String) {
    val characterImageRes = getCharacterImageRes(characterDescription)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .padding(
                start = if (message.isUser) 27.dp else 0.dp,
                end = if (message.isUser) 0.dp else 27.dp
            ),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!message.isUser) {
            Image(
                painter = painterResource(id = characterImageRes),
                contentDescription = characterDescription,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp)
            )
        }

        Column() {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = message.text,
                color = if (message.isUser) Color.Black else Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = if (message.isUser) Brown40.copy(alpha = 0.2f) else Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp)
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

fun getCharacterImageRes(characterDescription: String): Int {
    return when (characterDescription) {
        "Mozzi" -> R.drawable.char_mozzi1
        "BAO" -> R.drawable.char_bao1
        "SKY" -> R.drawable.char_sky1
        else -> R.drawable.char_mozzi1
    }
}
