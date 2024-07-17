package com.iccas.zen.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.report.ReportBox.ReportSelectBox

@Composable
fun EmotionDiarySelect(navController: NavController) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Emotional Diary",
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
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

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
                        text = "recent conversation",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp) // 내부 패딩 추가
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ReportSelectBox(navController, R.drawable.temp_char, "with BAO", "24/03/03")
            Spacer(modifier = Modifier.height(16.dp))
            ReportSelectBox(navController, R.drawable.temp_char, "with KINI", "24/03/03")
            Spacer(modifier = Modifier.height(16.dp))
            ReportSelectBox(navController, R.drawable.temp_char, "with LUCY", "24/03/03")
            Spacer(modifier = Modifier.height(16.dp))
            ReportSelectBox(navController, R.drawable.temp_char, "with SKY", "24/03/03")
        }
    }
}