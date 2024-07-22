package com.iccas.zen.presentation.character

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.character.characterViewModel.CharacterViewModel
import com.iccas.zen.presentation.components.BasicBackgroundWithNavBar
import com.iccas.zen.ui.theme.Grenn30
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

@Composable
fun CharScreen(navController: NavController, characterViewModel: CharacterViewModel = viewModel()) {
    val user = characterViewModel.user
    val testDate = user?.lastTestAt ?: "Loading..."

    if (testDate != "Loading...") {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        try {
            val lastTestDateTime = LocalDateTime.parse(testDate, formatter)
            val currentDateTime = LocalDateTime.now()

            Log.d("CharScreen", "Last test date: $lastTestDateTime, Current date: $currentDateTime")

            val daysBetween = ChronoUnit.DAYS.between(lastTestDateTime, currentDateTime)
            if (daysBetween > 7) {
                navController.navigate("survey")
            }
        } catch (e: DateTimeParseException) {
            Log.e("CharScreen", "Date parsing error: ${e.message}")
        }
    }

    // 캐릭터 선택 로직
    val selectedCharacter = user?.let {
        when {
            it.leaf >= 800 -> Characters[8]
            it.leaf >= 700 -> Characters[7]
            it.leaf >= 600 -> Characters[6]
            it.leaf >= 500 -> Characters[5]
            it.leaf >= 400 -> Characters[4]
            it.leaf >= 300 -> Characters[3]
            it.leaf >= 200 -> Characters[2]
            it.leaf >= 100 -> Characters[1]
            else -> Characters[0]
        }
    }

    val selectedBG = user?.background_id ?: 0

    // 배경 이미지 선택 로직
    val backgroundResource = when (selectedBG) {
        1 -> R.drawable.background2
        2 -> R.drawable.background3
        3 -> R.drawable.background4
        else -> R.drawable.background1
    }
    Log.d("SignupViewModel", "Status: "+backgroundResource.toString())


    // 경험치 및 레벨 계산 로직
    val (level, experience) = user?.leaf?.let { leaf ->
        val level = (leaf / 100) % 3 + 1
        val experience = leaf % 100
        Pair(level, experience)
    } ?: Pair(1, 0)

    // 경험치 바 채우기 애니메이션을 위한 상태 변수
    val animatedProgress = animateFloatAsState(targetValue = experience / 100f)

    BasicBackgroundWithNavBar(navController = navController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Level and Experience bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = selectedCharacter?.name ?: "Loading...", fontSize = 40.sp, color = Color.Black)
                Text(text = "LV. $level  $experience / 100", fontSize = 30.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .height(12.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(animatedProgress.value) // 경험치 바의 너비를 애니메이션으로 조절
                            .clip(CircleShape)
                            .background(Grenn30)
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            // Character and Background
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp)
                    .padding(bottom = 70.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = backgroundResource),
                    contentDescription = "Character Background",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12))
                        .alpha(0.6f),
                    contentScale = ContentScale.Crop
                )
                selectedCharacter?.let {
                    Image(
                        painter = painterResource(id = it.charImgId),
                        contentDescription = "Character",
                        modifier = Modifier.size(260.dp)
                    )
                }
            }
        }
    }
}
