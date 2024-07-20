package com.iccas.zen.presentation.heartreport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Gray80

@Composable
fun HeartReportScreen(navController: NavController) {
    val currentMonth = remember { mutableStateOf(6) } // 6월로 초기화
    val currentYear = remember { mutableStateOf(24) } // 24년으로 초기화
    val heartLives = mapOf(
        Pair(24 to 6, listOf(
            "game6" to 3,
            "game5" to 2,
            "game4" to 5,
            "game3" to 4,
            "game2" to 3,
            "game1" to 3
        ))
        ,Pair(24 to 5, listOf(
            "game5" to 5,
            "game4" to 4,
            "game3" to 3,
            "game2" to 2,
            "game1" to 1))

        // 다른 달과 연도의 기록을 추가할 수 있습니다.
    )

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate("report") },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 0.dp) // Remove padding to align with ZEN
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Back"
                    )
                }

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFFFFA500))) {
                            append("Anger Control")
                        }
                    },
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(4.dp)) // 8sp 공백 추가
                Text(
                    "Report",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                IconButton(onClick = {
                    if (currentMonth.value > 1) currentMonth.value--
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.go_left), // 왼쪽 화살표 아이콘 사용
                        contentDescription = "Previous Month"
                    )
                }

                // 날짜 텍스트
                Text(
                    text = "${currentYear.value}.${currentMonth.value.toString().padStart(2, '0')}",
                    color = Brown40,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )

                // 다음 달로 이동하는 버튼
                IconButton(onClick = {
                    if (currentMonth.value < 12) currentMonth.value++
                }
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.go_right),// 오른쪽 화살표 아이콘 사용
                        contentDescription = "Next Month"// 오른쪽 화살표로 변경

                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            val currentGameRecords = heartLives[Pair(currentYear.value, currentMonth.value)]
            if (currentGameRecords != null) {
                currentGameRecords.forEach { (game, lives) ->
                    GameScoreRow(game = game, lives = lives, navController = navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                Text(
                    text = "No game records for this month.",
                    color = Brown40,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun GameScoreRow(game: String, lives: Int, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(60.dp)
            .background(Gray80, RoundedCornerShape(50))
            .border(2.dp, Brown40, RoundedCornerShape(50)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { navController.navigate("heartreport2") },
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth()
                .padding(4.dp)
                .height(60.dp)
                .background(Color.Transparent, RoundedCornerShape(50))
                .border(0.dp, Color.Transparent, RoundedCornerShape(50)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Brown40
            )
        ) {
            Text(
                text = game,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 4.dp),
                fontFamily = FontFamily.Serif
            )
            Row(
                modifier = Modifier.padding(end = 16.dp)
            ) {
                repeat(5) { index ->
                    val heartIcon = if (index < lives) {
                        R.drawable.tetris_game_heart_filled
                    } else {
                        R.drawable.tetris_game_heart_unfilled
                    }
                    Image(
                        painter = painterResource(id = heartIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}
