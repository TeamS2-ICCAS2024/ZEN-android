package com.cookandroid.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cookandroid.myapplication.ui.theme.MyApplicationTheme

class WaitingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            Content(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = { navigateNext() })
                            BackButton(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp),
                                onClick = { navigateBack() }
                            )
                        }
                    }
                )
            }
        }
    }

    private fun navigateBack() {
        // MainActivity로 돌아가는 인텐트 설정
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateNext() {
        val intent = Intent(this, ShowingActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun Content(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Yoga Game",
            fontSize = 60.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.waiting),
            contentDescription = "Waiting Image",
            modifier = Modifier.size(400.dp) // 원하는 크기로 설정
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = modifier
        ) {
            Text("Play", color = Color.White, fontSize = 30.sp)
        }
    }
}
