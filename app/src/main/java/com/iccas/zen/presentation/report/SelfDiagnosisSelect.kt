package com.iccas.zen.presentation.report

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Gray80

@Composable
fun SelfDiagnosisSelect(
    navController: NavController
) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
        ) {
            IconButton(
                onClick = { navController.navigate("heartreport2") },
                modifier = Modifier
                    .padding(start = 0.dp) // Remove padding to align with ZEN
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Self Diagnosis",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA500) // 주황색
            )
            Text(
                text = " Report",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black // 검정색
            )
            Spacer(modifier = Modifier.width(40.dp))
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
                    .background(Color(0xFF8B4513).copy(alpha = 0.2f), RoundedCornerShape(20.dp)) // 어두운 갈색 형광펜 효과
            ) {
                Text(
                    text = "diagnosis history",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Brown40,
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
fun DiagnosisBox(
    navController: NavController, diagnosisName: String, date: String,
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(80.dp)
            .background(Gray80, RoundedCornerShape(50))
            .border(2.dp, Brown40, RoundedCornerShape(50))
            .clickable { navController.navigate("survey") },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = diagnosisName,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                fontFamily = FontFamily.Serif,
                color = Brown40
            )
            Text(
                text = date,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                color = Brown40
            )
        }
    }
}
