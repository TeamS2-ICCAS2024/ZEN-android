package com.iccas.zen.presentation.heart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.heart.heartComponents.BaselineRecord
import com.iccas.zen.presentation.heart.heartComponents.HeartRateRecord
import com.iccas.zen.presentation.heart.viewmodel.MeasureHeartViewModel

@Composable
fun BaseResultScreen(
    measureHeartViewModel: MeasureHeartViewModel,
    navController: NavController
) {
    val averageHeartRate = measureHeartViewModel.averageHeartRate.collectAsState().value
    val currentHeartRate = measureHeartViewModel.receivedData.collectAsState().value

    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "The baseline\nmeasurement has been\ncompleted!",
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            BaselineRecord("Baseline HR: ${averageHeartRate ?: "N/A"}")
            HeartRateRecord(type = "Current HR: $currentHeartRate")
            Spacer(modifier = Modifier.height(50.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    navController.navigate("countdown/tetris_game")
                }) {
                Text("Start game !")
            }
        }
    }
}