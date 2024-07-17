package com.iccas.zen.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun SelfDiagnosisSelect(
    navController: NavController
) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Self Diagnosis",
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
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF8B4513).copy(alpha = 0.2f), RoundedCornerShape(4.dp)) // 어두운 갈색 형광펜 효과
            ) {
                Text(
                    text = "diagnosis history",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp) // 내부 패딩 추가
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Diagnosis list
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    DiagnosisBox(navController = navController, diagnosisName = "Diagnosis", date = "24/03/03")
                    DiagnosisBox(navController = navController, diagnosisName = "Diagnosis", date = "24/03/10")
                    DiagnosisBox(navController = navController, diagnosisName = "Diagnosis", date = "24/03/11")
                    DiagnosisBox(navController = navController, diagnosisName = "Diagnosis", date = "24/03/12")
                    DiagnosisBox(navController = navController, diagnosisName = "Diagnosis", date = "24/03/13")
            }
        }
    }
}

@Composable
fun DiagnosisBox(navController: NavController, diagnosisName: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE0E0E0))
            .padding(25.dp)
            .clickable {
                // 네비게이션 경로 설정
                navController.navigate("self_diagnosis_report/$diagnosisName")
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = diagnosisName,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A8A8A)
        )

        Text(
            text = date,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A8A8A)
        )
    }
}