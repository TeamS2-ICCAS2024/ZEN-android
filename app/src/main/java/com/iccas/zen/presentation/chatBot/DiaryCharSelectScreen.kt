package com.iccas.zen.presentation.chatBot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.character.characterViewModel.CharacterViewModel
import com.iccas.zen.presentation.components.BasicBackgroundWithNavBar
import com.iccas.zen.presentation.home.components.TitleSticker

@Composable
fun DiaryCharSelectScreen(
    navController: NavController,
    characterViewModel: CharacterViewModel = viewModel()
) {
    val user = characterViewModel.user
    val leaf = user?.leaf ?: 0

    val visibleCharacters = when {
        leaf >= 600 -> listOf("Mozzi", "Bao", "Sky")
        leaf >= 300 -> listOf("Mozzi", "Bao")
        else -> listOf("Mozzi")
    }

    BasicBackgroundWithNavBar(navController = navController) {
        Spacer(modifier = Modifier.height(40.dp))

        TitleSticker(R.drawable.home_title_blue_sticker, "Emotion Diary")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            visibleCharacters.forEach { character ->
                val characterImageRes = getCharacterImageRes(character, leaf)
                CharacterRow(character, characterImageRes, navController)
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
fun CharacterRow(character: String, characterImageRes: Int, navController: NavController) {
    val characterDescription = when (character) {
        "Mozzi" -> "Mozzi"
        "Bao" -> "BAO"
        "Sky" -> "SKY"
        else -> "default character"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = characterImageRes),
            contentDescription = null,
            modifier = Modifier.size(73.dp)
        )
        Text(
            text = characterDescription,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .size(70.dp, 35.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(50), clip = false) // 그림자를 아래쪽에만 추가
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray)
                .clickable { navController.navigate("select_emotion/$characterImageRes/$characterDescription") },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "chat",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun getCharacterImageRes(character: String, leaf: Int): Int {
    return when (character) {
        "Mozzi" -> when {
            leaf >= 200 -> R.drawable.char_mozzi3
            leaf >= 100 -> R.drawable.char_mozzi2
            else -> R.drawable.char_mozzi1
        }
        "Bao" -> when {
            leaf >= 500 -> R.drawable.char_bao3
            leaf >= 400 -> R.drawable.char_bao2
            else -> R.drawable.char_bao1
        }
        "Sky" -> when {
            leaf >= 800 -> R.drawable.char_sky3
            leaf >= 700 -> R.drawable.char_sky2
            else -> R.drawable.char_sky1
        }
        else -> R.drawable.char_mozzi1
    }
}
