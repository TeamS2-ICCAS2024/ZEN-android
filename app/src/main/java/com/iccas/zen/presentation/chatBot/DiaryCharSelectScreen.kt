package com.iccas.zen.presentation.chatBot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.character.CharacterViewModel
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
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = characterDescription,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Button(
            onClick = { navController.navigate("select_emotion") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(text = "chat", color = Color.Black, fontSize = 20.sp)
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