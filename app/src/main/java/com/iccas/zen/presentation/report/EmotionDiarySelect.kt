package com.iccas.zen.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun EmotionDiarySelect(navController: NavController) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
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
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ReportSelectBox(navController, R.drawable.char_mozzi1, "with BAO", "24/03/03")
            Spacer(modifier = Modifier.height(16.dp))
            ReportSelectBox(navController, R.drawable.char_mozzi1, "with KINI", "24/03/03")
            Spacer(modifier = Modifier.height(16.dp))
            ReportSelectBox(navController, R.drawable.char_mozzi1, "with LUCY", "24/03/03")
            Spacer(modifier = Modifier.height(16.dp))
            ReportSelectBox(navController, R.drawable.char_mozzi1, "with SKY", "24/03/03")
        }
    }
}
@Composable
fun ReportSelectBox(navController: NavController, imageRes: Int, character: String, date: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable {
                navController.navigate("report_detail/${character.encode()}/${date.encode()}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = character, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = date, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}

fun String.encode(): String = java.net.URLEncoder.encode(this, "UTF-8")

