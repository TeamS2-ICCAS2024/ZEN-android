package com.iccas.zen.presentation.diagnosis

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackgroundWithLogo
import com.iccas.zen.ui.theme.Brown40

@Composable
fun SurveyScreen(navController: NavController) {
    BasicBackgroundWithLogo {
        var selectedAnswers by remember { mutableStateOf(List(10) { "" }) }
        var showWarning by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),
                shape = RoundedCornerShape(16.dp), // 둥근 모서리
                border = BorderStroke(1.dp, color = Brown40) // 검은색 테두리
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(4.dp)
                ) {


                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.chat_angry),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Color.Red)) {
                                        append("Anger")
                                    }
                                    append(" Self-Test")
                                },
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                    }

                    val questions = listOf(
                        "Q1. I am quick-tempered.",
                        "Q2. I have a fiery temper.",
                        "Q3. I am easily agitated.",
                        "Q4. I get angry when someone else's mistake delays my work.",
                        "Q5. I feel furious when my good work is not recognized by others.",
                        "Q6. I easily get angry.",
                        "Q7. I curse when I am angry.",
                        "Q8. I get furious when criticized in front of others.",
                        "Q9. When my work is hindered, I feel like hitting someone.",
                        "Q10. I feel furious when I get a bad evaluation despite my good work."
                    )

                    items(questions.size) { index ->
                        QuestionRow(
                            question = questions[index],
                            selectedAnswer = selectedAnswers[index],
                            onAnswerSelected = { answer ->
                                selectedAnswers = selectedAnswers.toMutableList().apply {
                                    this[index] = answer
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        val totalScore = calculateTotalScore(selectedAnswers)
                        Spacer(modifier = Modifier.height(20.dp))
                        if (showWarning) {
                            Text(
                                text = "Check all questions",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Button(
                            onClick = {
                                if (totalScore < 10) {
                                    showWarning = true
                                } else {
                                    navController.navigate("result/$totalScore")
                                }
                            },
                            // Button styling...
                        ) {
                            Text("Check the result")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionRow(question: String, selectedAnswer: String, onAnswerSelected: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = question,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround // 각 항목 사이에 공간 추가
        ) {
            val answers = listOf("Not at all", "Sometimes", "Often", "Very Often")
            answers.forEach { answer ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 4.dp) // 각 항목 사이에 가로 여백 추가
                        .weight(1.1f) // 각 항목의 공간을 더 넓게 배분
                ) {
                    RadioButton(
                        selected = (selectedAnswer == answer),
                        onClick = { onAnswerSelected(answer) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF705E5E)
                        )
                    )
                    Text(
                        text = answer,
                        fontSize = 12.sp, // 글씨 크기를 12.sp로 유지
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

fun calculateTotalScore(selectedAnswers: List<String>): Int {
    val scoreMap = mapOf(
        "Not at all" to 1,
        "Sometimes" to 2,
        "Often" to 3,
        "Very Often" to 4
    )
    return selectedAnswers.sumOf { scoreMap[it] ?: 0 }
}
