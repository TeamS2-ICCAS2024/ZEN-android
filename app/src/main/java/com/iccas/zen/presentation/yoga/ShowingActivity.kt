package com.cookandroid.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

class ShowingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val poseIndex = intent.getIntExtra("poseIndex", 0)
        val currentPose = yogaPoses[poseIndex]

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
                                pose = currentPose
                            )
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

        // 4초 후에 DoingYogaActivity로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToDoingYoga(poseIndex)
        }, 4000)
    }

    private fun navigateBack() {
        // MainActivity로 돌아가는 인텐트 설정
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDoingYoga(poseIndex: Int) {
        val intent = Intent(this, DoingYogaActivity::class.java).apply {
            putExtra("poseIndex", poseIndex)
        }
        startActivity(intent)
        finish() // 현재 액티비티를 종료하여 뒤로 가기 시 돌아오지 않게 함
    }
}

@Composable
fun Content(modifier: Modifier = Modifier, pose: YogaPose) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(16.dp))  // 상단 여백
        Text(
            text = "Follow the next move",
            fontSize = 40.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))  // 텍스트와 이미지 사이 여백
        Image(
            painter = painterResource(id = pose.imageRes), // 요가 자세 이미지
            contentDescription = "Yoga Pose Image",
            modifier = Modifier.size(400.dp) // 이미지 크기 설정
        )
        Text(
            text = pose.name,
            fontSize = 30.sp,
            color = Color.Black
        )
    }
}
