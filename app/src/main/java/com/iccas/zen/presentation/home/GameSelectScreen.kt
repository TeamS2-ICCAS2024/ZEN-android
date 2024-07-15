package com.iccas.zen.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.home.components.GameSelectBox
import com.iccas.zen.presentation.home.components.TitleSticker

@Composable
fun GameSelectScreen(
    navController: NavController
) {
    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(40.dp))

        TitleSticker(R.drawable.home_title_pink_sticker, "Play Game")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameSelectBox(
                type = "tetris",
                onClick = { navController.navigate("guide_measure_base") })
            Spacer(modifier = Modifier.height(30.dp))
            GameSelectBox(type = "yoga", onClick = {})
            GameSelectBox(
                type = "chat",
                onClick = { navController.navigate("") })
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}