package com.iccas.zen.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.components.TitleWithHighligher
import com.iccas.zen.presentation.report.reportComponents.ReportTitle
import com.iccas.zen.presentation.report.viewModel.ReportViewModel
import com.iccas.zen.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iccas.zen.data.dto.emotionDiary.response.DiaryEntry
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Gray80

@Composable
fun EmotionDiarySelectScreen(
    navController: NavController
) {
    val reportViewModel: ReportViewModel = viewModel()
    val diaryListState = reportViewModel.diaryList.collectAsState()

    LaunchedEffect(Unit) {
        reportViewModel.getDiaryList()
    }

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ReportTitle(
                backOnClick = { navController.navigate("report") },
                highlightText = "Emotion Diary"
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box {
                            TitleWithHighligher(
                                title = "recent conversation",
                                highLighterWidth = 230.dp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
                // 서버에서 가져온 일기 목록을 표시
                items(diaryListState.value) { diary ->
                    EmotionDiaryBox(
                        diary = diary,
                        onClick = {
                            navController.navigate("emotion_detail/${diary.emotionDiaryId}")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun EmotionDiaryBox(
    diary: DiaryEntry,
    onClick: () -> Unit
) {
    val imageRes = when (diary.character) {
        "BAO" -> R.drawable.char_bao1
        "Mozzi" -> R.drawable.char_mozzi1
        "SKY" -> R.drawable.char_sky1
        else -> R.drawable.chat_bao
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Gray80, RoundedCornerShape(50))
            .border(2.dp, Brown40, RoundedCornerShape(50))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Transparent, CircleShape),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "with " + diary.character,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black,
                    fontSize = 23.sp
                )
            }
            Text(text = diary.date, color = Brown40, fontSize = 15.sp)
        }
    }
}
