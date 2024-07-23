package com.iccas.zen.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.components.UserViewModel
import com.iccas.zen.presentation.components.TitleWithHighligher
import com.iccas.zen.presentation.report.reportComponents.ReportTitle
import com.iccas.zen.utils.formatLocalDateTime

@Composable
fun SelfDiagnosisReportScreen(
    navController: NavController,
    testId: Int,
    userViewModel: UserViewModel = viewModel()
) {
    val selfTestResults = userViewModel.selfTestResults.collectAsState()

    val currentIndex = selfTestResults.value.indexOfFirst { it.id == testId }
    val recentTestResults = if (currentIndex != -1) {
        val startIndex = (currentIndex - 3).coerceAtLeast(0)
        selfTestResults.value.subList(startIndex, currentIndex + 1).takeLast(4)
    } else {
        emptyList()
    }

    val testResult = selfTestResults.value.find { it.id == testId }

    val scoreMessage = when (testResult?.score) {
        in 10..15 -> "Your score is within a normal range !\nYou're doing great :)"
        in 16..25 -> "You experience mild anger. It is not a significant problem and you can control your anger well.\nTry to have a little more peace of mind."
        in 26..28 -> "You likely experience considerable anger. You may have had moments where you regret your actions or face difficulties in relationships. Seeking professional help is recommended."
        in 29..40 -> "You often experience anger. You may find it difficult to maintain normal relationships. Seeking professional help is strongly recommended for your well-being and the well-being of those around you."
        else -> "Loading..."
    }

    BasicBackgroundWithLogo {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            ReportTitle(backOnClick = { navController.navigate("report/self_diagnosis") }, highlightText = "Self Diagnosis")

            Spacer(modifier = Modifier.height(40.dp))

            TitleWithHighligher(title = "Recent", highLighterWidth = 80.dp)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                recentTestResults.forEach { result ->
                    val formattedDate = formatLocalDateTime(result.createdAt, "MM/dd")
                    val emojiResId = when (result.score) {
                        in 10..15 -> R.drawable.chat_happy
                        in 16..25 -> R.drawable.chat_soso
                        in 26..28 -> R.drawable.chat_sad
                        in 29..40 -> R.drawable.chat_angry
                        else -> R.drawable.chat_soso // Default icon
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            EmojiIcon(emojiResId)
                            Text(text = formattedDate)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            TitleWithHighligher(title = "Score", highLighterWidth = 70.dp)
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = testResult?.score?.toString() ?: "Loading...",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.memo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = scoreMessage,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(25.dp)
                )
            }
        }
    }
}
@Composable
fun EmojiIcon(resId: Int) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = null,
        modifier = Modifier
            .size(80.dp)
            .background(Color.Transparent, CircleShape)
            .padding(8.dp),
        contentScale = ContentScale.Fit
    )
}