package com.iccas.zen.presentation.diagnosis

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo

@Composable
fun DiagnosisScreen(navController: NavController) {
    BasicBackgroundWithLogo {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.diagtext),
                    contentDescription = null,
                    modifier = Modifier
                        .width(350.dp)
                        .height(350.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))
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
                            .clickable { navController.navigate("survey") },
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.heart_negative_response_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .width(105.dp)
                            .clickable { navController.navigate("heartreport") },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

