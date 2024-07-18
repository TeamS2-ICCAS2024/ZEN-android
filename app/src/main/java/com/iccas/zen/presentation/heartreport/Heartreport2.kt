package com.iccas.zen.presentation.heartreport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.ui.theme.Brown40

@Composable
fun HeartReport2Screen(navController: NavController) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // Header with Back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate("heartreport") },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 0.dp) // Remove padding to align with ZEN
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.width(4.dp)) // Adjust spacing as needed
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFFFFA500))) {
                            append("Anger Control")
                        }
                    },
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp)) // Slight space between the texts
                Text(
                    "Report",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        Color(0xFF8B4513).copy(alpha = 0.1f),
                        RoundedCornerShape(20.dp),


                    ),    contentAlignment = Alignment.TopStart
                // 어두운 갈색 형광펜 효과
            ) {
                Text(
                    text = "heart rate",
                    color = Brown40, // Assuming Brown40 corresponds to this color
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 2.dp)
                        .align(Alignment.CenterStart)
                )
            }

            // Heart Rate Graph


            Image(
                painter = painterResource(id = R.drawable.chat_happy),
                contentDescription = "Heart Rate Graph",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )

            Box(
                modifier = Modifier

                    .background(
                        Color(0xFF8B4513).copy(alpha = 0.1f),
                        RoundedCornerShape(20.dp)
                    ) // 어두운 갈색 형광펜 효과
            ) {
                Text(
                    color = Brown40,
                    text = "overview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 2.dp)
                )
            }

            // 3행 2열 형식의 Overview details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.width(100.dp)
                        .padding(bottom = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OverviewxColumn(title = "level", value = "9")
                    OverviewxColumn(title = "play time", value = "7:00")
                    OverviewxColumn(title = "average\nheart rate", value = "97")
                }
                Spacer(modifier = Modifier.width(100.dp))
                Column(
                    modifier = Modifier.width(100.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OverviewxColumn(title = "score", value = "70000")
                    OverviewxColumn(title = "Lives", value = "3")
                    OverviewxColumn(title = "When", value = "24/05/30")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
@Composable
fun OverviewxColumn(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(text = value, fontSize = 20.sp, textAlign = TextAlign.Center)
    }
}
