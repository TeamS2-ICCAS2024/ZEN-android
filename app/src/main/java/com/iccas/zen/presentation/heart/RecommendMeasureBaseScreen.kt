package com.iccas.zen.presentation.heart

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun RecommendMeasureBaseScreen(
    navController: NavController
) {
    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.heart_speech_bubble),
                    contentDescription = null,
                    modifier = Modifier
                        .width(340.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(bottom = 50.dp),
                    text = "We recommend\nmeasuring baseline\n heart rate first.",
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
            Spacer(modifier = Modifier.height(80.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heart_positive_response_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .clickable { navController.navigate("measure_base") },
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.heart_negative_response_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(105.dp)
                        .clickable { navController.navigate("tetris_game") },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}