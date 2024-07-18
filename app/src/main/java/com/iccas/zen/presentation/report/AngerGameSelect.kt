package com.iccas.zen.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.R


@Composable
fun AngerGameSelect(navController: NavController) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Anger Control",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA500) // 주황색
            )
            Text(
                text = " Report",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black // 검정색
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Date selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF8B4513).copy(alpha = 0.2f), RoundedCornerShape(4.dp)) // 어두운 갈색 형광펜 효과
            ) {
                Text(
                    text = "Game History",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp) // 내부 패딩 추가
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Game report list
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 6 downTo 1) {
                GameReportBox(navController = navController, gameName = "game$i", score = i)
            }
        }
    }
}

@Composable
fun GameReportBox(navController: NavController, gameName: String, score: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE0E0E0))
            .border(3.dp, Color.Gray, RoundedCornerShape(20.dp))
            .padding(25.dp)
            .clickable {
                navController.navigate("anger_game_report/$gameName") // 네비게이션 경로 설정
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = gameName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A8A8A)
        )

        Row {
            for (i in 1..5) {
                val heartRes = if (i <= score) R.drawable.heart_rate_icon else R.drawable.heart_outline
                Image(
                    painter = painterResource(id = heartRes),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}