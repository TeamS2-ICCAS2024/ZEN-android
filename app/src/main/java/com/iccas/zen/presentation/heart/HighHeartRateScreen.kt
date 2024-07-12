package com.iccas.zen.presentation.heart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackground
import kotlinx.coroutines.delay

@Composable
fun HighHeartRateScreen() {
    var currentIndex by remember { mutableIntStateOf(0) }
    val images = listOf(
        R.drawable.heart_breaths_img1,
        R.drawable.heart_breaths_img2,
    )
    val texts = listOf(
        "Take deep breaths\nto stabilize :)\nYou're doing great!",
        "Once your heart rate\nstabilizes, the game\nwill start again!"
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            currentIndex = (currentIndex + 1) % images.size
        }
    }

    BasicBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = images[currentIndex]),
                contentDescription = null,
                modifier = Modifier.width(205.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = texts[currentIndex],
                fontSize = 22.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
        }
    }
}
