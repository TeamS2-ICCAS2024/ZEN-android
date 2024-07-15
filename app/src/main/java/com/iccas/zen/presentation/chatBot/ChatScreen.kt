
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import com.iccas.zen.presentation.chatBot.ChatViewModel
import com.iccas.zen.presentation.chatBot.Message

@Composable
fun ChatScreen(navController: NavHostController, emojiResId: Int, viewModel: ChatViewModel = viewModel(), scrollState: ScrollState) {
    BasicBackgroundWithLogo {
        val messages by viewModel.messages.collectAsState()
        var inputText by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val view = LocalView.current
        var imeHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        LaunchedEffect(messages.size) {
            listState.animateScrollToItem(0)
        }

        DisposableEffect(view) {
            val listener = ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
                // IME(키보드)가 보이는지 여부를 확인합니다.

                imeHeight = if (imeVisible) {
                    with(density) { insets.getInsets(WindowInsetsCompat.Type.ime()).bottom.toDp() }
                } else {
                    0.dp
                }
                // IME가 보이면 IME의 높이를 dp로 변환하여 imeHeight 상태에 저장합니다.
                // 그렇지 않으면 imeHeight를 0dp로 설정합니다.

                insets
            }
            onDispose { ViewCompat.setOnApplyWindowInsetsListener(view, null) }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                reverseLayout = true, // Use reverse layout
                verticalArrangement = Arrangement.Top
            ) {
                items(messages.asReversed()) { message -> // Reverse the message order
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
                        .background(Color(0xFF0087B3), CircleShape) // Sky blue background with circular shape
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_up), // Use the specified icon resource
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
                painter = painterResource(id = emojiResId), // User emoji resource
                contentDescription = "User",
                modifier = Modifier.size(50.dp).padding(start = 8.dp)
            )
        }
    }
}