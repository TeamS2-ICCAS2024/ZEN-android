package com.iccas.zen.presentation.diagnosis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.ui.theme.Brown40

@Composable
fun ResultScreen(score: Int, navController: NavController) {
    val message = when (score) {
        in 10..15 -> "Your score is within a normal range."
        in 16..25 -> "You experience mild anger. It is not a significant problem and you can control your anger well. Try to have a little more peace of mind."
        in 26..28 -> "You likely experience considerable anger. You may have had moments where you regret your actions or face difficulties in relationships. Seeking professional help is recommended."
        in 29..40 -> "You often experience anger. You may find it difficult to maintain normal relationships. Seeking professional help is strongly recommended for your well-being and the well-being of those around you."
        else -> "Invalid score."
    }

    BasicBackgroundWithLogo {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
                 {
                    Text(
                        text = "Thank you for taking the test!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp,top=12.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.clapgirl),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                     Text(
                         text = "Here is the result!",
                         fontWeight = FontWeight.Bold,
                         fontSize = 20.sp,
                         modifier = Modifier.padding(bottom = 8.dp)
                     )
                     Spacer(modifier = Modifier.height(16.dp))

                     Text(
                         fontWeight = FontWeight.ExtraBold,
                        text = message,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
