package com.iccas.zen.presentation.character

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithNavBar

@Composable
fun CharScreen(navController: NavController) {
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

                Text(text = "MOZZI", fontSize = 40.sp, color = Color.Black)
                Text(text = "LV. 1  80 / 100", fontSize = 30.sp, color = Color.Black)
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
                            .fillMaxWidth(0.8f)
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
                    painter = painterResource(id = R.drawable.temp_background), // 첫 번째 이미지
                    contentDescription = "Character Background",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(60.dp))
                        .alpha(0.6f),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.temp_char), // 두 번째 이미지
                    contentDescription = "Character",
                    modifier = Modifier.size(250.dp)
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

        }
    }
}
