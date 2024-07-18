package com.iccas.zen.presentation.character

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.iccas.zen.presentation.components.BasicBackgroundWithNavBar

@Composable
fun CharScreen(navController: NavController, characterViewModel: CharacterViewModel = viewModel()) {
    val user = characterViewModel.user

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

    val selectedBG = user?.backgroundId ?: 0

    // 배경 이미지 선택 로직
    val backgroundResource = when (selectedBG) {
        1 -> R.drawable.background2
        2 -> R.drawable.background3
        3 -> R.drawable.background4
        else -> R.drawable.background1
    }

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
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top bar with logo and settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side is handled by BasicBackgroundWithLogo
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    navController.navigate("setting")
                }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }
            }

            // Level and Experience bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

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
                            .background(Color(0xFF66CC66)) // 짙은 녹색
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))

            // Character and Background
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = backgroundResource), // 선택된 배경 이미지
                    contentDescription = "Character Background",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(60.dp))
                        .alpha(0.6f),
                    contentScale = ContentScale.Crop
                )
                selectedCharacter?.let {
                    Image(
                        painter = painterResource(id = it.charImgId), // 캐릭터 이미지
                        contentDescription = "Character",
                        modifier = Modifier.size(250.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
