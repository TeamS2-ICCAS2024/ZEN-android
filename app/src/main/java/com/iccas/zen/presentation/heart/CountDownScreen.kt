package com.iccas.zen.presentation.heart

import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackground

@Composable
fun CountDownScreen(
    navController: NavController
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    val images = listOf(
        R.drawable.heart_countdown_three,
        R.drawable.heart_countdown_two,
        R.drawable.heart_countdown_one
    )

    LaunchedEffect(Unit) {
        repeat(2) {
            delay(1000L)
            currentImageIndex = (currentImageIndex + 1) % images.size
        }
        delay(1000L)
        navController.navigate("tetris_game")
    }

    BasicBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp, horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = images[currentImageIndex]),
                contentDescription = null,
                modifier = Modifier.height(180.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
