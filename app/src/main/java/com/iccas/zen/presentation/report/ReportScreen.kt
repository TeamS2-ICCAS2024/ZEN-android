package com.iccas.zen.presentation.report

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
import com.iccas.zen.presentation.components.BasicBackgroundWithNavBar
import com.iccas.zen.presentation.home.components.TitleSticker
import com.iccas.zen.presentation.report.reportComponents.ReportSelectBox

@Composable
fun ReportScreen(
    navController: NavController
) {
    BasicBackgroundWithNavBar(navController= navController) {
        Spacer(modifier = Modifier.height(40.dp))

        TitleSticker(R.drawable.home_title_green_sticker, "Report")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReportSelectBox(
                type = "Emotion Diary",
                onClick = { navController.navigate("report/emotion_diary") })
            Spacer(modifier = Modifier.height(60.dp))

            ReportSelectBox(
                type = "Anger Control",
                onClick = { navController.navigate("report/game") })
            Spacer(modifier = Modifier.height(60.dp))

            ReportSelectBox(
                type = "Self Diagnosis",
                onClick = { navController.navigate("report/self_diagnosis") })
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}