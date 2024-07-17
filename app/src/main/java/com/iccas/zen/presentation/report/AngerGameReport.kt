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
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun AngerGameReport(
    navController: NavController,
    gameName: String
) {
    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Title
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
                    text = " Game Report",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // 검정색
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Heart Rate Section
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
                        text = "heart rate",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp) // 내부 패딩 추가
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            Spacer(modifier = Modifier.height(20.dp))

            // Overview Section
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
                        text = "overview",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp) // 내부 패딩 추가
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OverviewRow(label = "level", value = "9")
                OverviewRow(label = "score", value = "70000")
                OverviewRow(label = "play time", value = "7:00")
                OverviewRow(label = "heart rate deviations", value = "3")
                OverviewRow(label = "average heart rate", value = "97")
                OverviewRow(label = "When", value = "24/05/30")
            }
        }
    }
}

@Composable
fun OverviewRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = value,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}