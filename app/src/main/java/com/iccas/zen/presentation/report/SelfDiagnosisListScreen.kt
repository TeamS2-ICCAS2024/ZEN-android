package com.iccas.zen.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.components.UserViewModel
import com.iccas.zen.presentation.components.TitleWithHighligher
import com.iccas.zen.presentation.report.reportComponents.ReportTitle
import com.iccas.zen.ui.theme.Brown40
import com.iccas.zen.ui.theme.Gray80
import com.iccas.zen.utils.formatLocalDateTime
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun SelfDiagnosisListScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val selfTestResults by userViewModel.selfTestResults.collectAsState(initial = emptyList())
    val reversedResults = selfTestResults.reversed()

    BasicBackgroundWithLogo {
        Spacer(modifier = Modifier.height(40.dp))
        ReportTitle(backOnClick = { navController.navigate("report") }, highlightText = "Self Diagnosis")
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TitleWithHighligher(title = "diagnosis history", highLighterWidth = 200.dp)

            Spacer(modifier = Modifier.height(20.dp))

            // Diagnosis list
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(reversedResults) { result ->
                    val formattedDate = formatLocalDateTime(result.createdAt)
                    DiagnosisBox(
                        navController = navController,
                        date = formattedDate,
                        id = result.id,
                        score = result.score
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
fun DiagnosisBox(
    navController: NavController,
    date: String,
    id: Int,
    score: Int
) {
    val emojiResId = when (score) {
        in 10..15 -> R.drawable.chat_happy
        in 16..25 -> R.drawable.chat_soso
        in 26..28 -> R.drawable.chat_sad
        in 29..40 -> R.drawable.chat_angry
        else -> R.drawable.chat_soso // Default icon
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Gray80, RoundedCornerShape(50))
            .border(2.dp, Brown40, RoundedCornerShape(50))
            .clickable { navController.navigate("report/self_diagnosis/$id") },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = emojiResId),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Transparent, CircleShape),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = date,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                color = Brown40
            )
        }
    }
}
