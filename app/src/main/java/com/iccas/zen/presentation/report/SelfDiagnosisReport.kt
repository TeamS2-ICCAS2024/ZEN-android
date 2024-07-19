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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.iccas.zen.presentation.components.CommonViewModel

@Composable
fun SelfDiagnosisReport(
    navController: NavController,
    testId: Int,
    commonViewModel: CommonViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val selfTestResults = commonViewModel.selfTestResults.collectAsState()

    val testResult = selfTestResults.value.find { it.id == testId }
    val recentTestResults = selfTestResults.value.takeLast(4)

    val scoreMessage = when (testResult?.score) {
        in 10..15 -> "Your score is within a normal range."
        in 16..25 -> "You experience mild anger. It is not a significant problem and you can control your anger well. Try to have a little more peace of mind."
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

            Text(
                text = "Self Diagnosis Report",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Recent",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                recentTestResults.forEach { result ->
                    val emojiResId = when (result.score) {
                        in 10..15 -> R.drawable.chat_happy
                        in 16..25 -> R.drawable.chat_soso
                        in 26..28 -> R.drawable.chat_sad
                        in 29..40 -> R.drawable.chat_angry
                        else -> R.drawable.chat_soso // Default icon
                    }
                    EmojiIcon(emojiResId)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(29.dp))

            Text(
                text = testResult?.score?.toString() ?: "Loading...",
                fontSize = 20.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
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