package com.iccas.zen.presentation.onBoarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackground
import com.iccas.zen.ui.theme.Brown40

@Composable
fun OnboardingPage(
    navController: NavController,
    imageResId: Int,
    title: String,
    description: String,
    nextPage: String,
    showPrev: Boolean = false,
    showNext: Boolean = true,
    currentPage: Int,
    totalPages: Int,
    imgSize: Dp
) {
    BasicBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_paper_with_tape), // 테이프가 붙은 종이 이미지
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .align(Alignment.Center)
                        .clipToBounds()
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier.size(imgSize)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            // Get Started Button
            Button(
                onClick = { navController.navigate("welcome") },
                colors = ButtonDefaults.buttonColors(containerColor = Brown40),
                shape = CircleShape,
                border = BorderStroke(2.dp, color = Brown40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Text(text = "Get Started!", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Page Indicator and Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Button(
                    onClick = { if (showPrev) navController.popBackStack() },
                    enabled = showPrev, // 비활성화 여부 설정
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (showPrev) Color.White else Color.Gray
                    ),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, color = Brown40),
                    modifier = Modifier.size(80.dp)
                ) {
                    Text(text = "Prev", color = if (showPrev) Brown40 else Color.DarkGray, textAlign = TextAlign.Center)
                }

                Row() {
                    repeat(totalPages) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (index == currentPage) Color.Gray else Color.LightGray,
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }

                Button(
                    onClick = { if (showNext) navController.navigate(nextPage) },
                    enabled = showNext, // 비활성화 여부 설정
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (showNext) Color.White else Color.Gray
                    ),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, color = Brown40),
                    modifier = Modifier.size(80.dp)
                ) {
                    Text(text = "Next", color = if (showNext) Brown40 else Color.DarkGray)
                }
            }

        }
    }
}

@Composable
fun OnboardingPage1(navController: NavController) {
    OnboardingPage(
        navController = navController,
        imageResId = R.drawable.measure_heart_rate,
        title = "Measure Heart Rate",
        description = "Practice relaxation\nthrough heart rate\nmonitoring.",
        nextPage = "onboarding2",
        showPrev = false,
        showNext = true,
        currentPage = 0,
        totalPages = 4,
        imgSize = 190.dp
    )
}

@Composable
fun OnboardingPage2(navController: NavController) {
    OnboardingPage(
        navController = navController,
        imageResId = R.drawable.mindful_yoga,
        title = "Mindful Yoga",
        description = "Manage the mind\nthrough yoga.",
        nextPage = "onboarding3",
        showPrev = true,
        showNext = true,
        currentPage = 1,
        totalPages = 4,
        imgSize = 200.dp
    )
}

@Composable
fun OnboardingPage3(navController: NavController) {
    OnboardingPage(
        navController = navController,
        imageResId = R.drawable.self_assessment,
        title = "Self-Assessment",
        description = "Analyze your condition\nthrough regular\nself-assessment.",
        nextPage = "onboarding4",
        showPrev = true,
        showNext = true,
        currentPage = 2,
        totalPages = 4,
        imgSize = 160.dp
    )
}

@Composable
fun OnboardingPage4(navController: NavController) {
    OnboardingPage(
        navController = navController,
        imageResId = R.drawable.reflect_on_your_day,
        title = "Reflect On Your Day",
        description = "Write an emotion diary\nthrough conversations\nwith the chatbot.",
        nextPage = "main",
        showPrev = true,
        showNext = false,
        currentPage = 3,
        totalPages = 4,
        imgSize = 170.dp
    )
}
