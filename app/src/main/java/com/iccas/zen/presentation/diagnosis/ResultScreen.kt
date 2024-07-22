package com.iccas.zen.presentation.diagnosis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.presentation.components.CommonViewModel
import com.iccas.zen.ui.theme.Brown40
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(score: Int, navController: NavController, commonViewModel: CommonViewModel = viewModel() ) {
    val message = when (score) {
        in 10..15 -> "Your score is within a normal range."
        in 16..25 -> "You experience mild anger.It is not a significant problem and you can control your anger well. Try to have a little more peace of mind."
        in 26..28 -> "You likely experience considerable anger. You may have had moments where you regret your actions or face difficulties in relationships. Seeking professional help is recommended."
        in 29..40 -> "You often experience anger.\nYou may find it difficult to\nmaintain normal relationships.\nSeeking professional help is\nstrongly recommended for your well-being and the well-being of\nthose around you."
        else -> "Invalid score."
    }

    LaunchedEffect(true) {
        delay(10000L) // Wait for 5 seconds
        navController.navigate("char_main") // Navigate to char_main
    }

    LaunchedEffect(score) {
        commonViewModel.postscore(score)
    }
    BasicBackgroundWithLogo {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Thank you for\ntaking the test!",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.survey_clap_girl),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

             Text(
                 text = "Here is the result!",
                 fontWeight = FontWeight.Bold,
                 fontSize = 23.sp
             )
             Spacer(modifier = Modifier.height(16.dp))

             Text(
                 fontWeight = FontWeight.ExtraBold,
                text = message,
                fontSize = 16.sp,
                color = Brown40,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

