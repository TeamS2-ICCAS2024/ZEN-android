package com.iccas.zen.data.dto.auth.request

data class EmotionDiaryPostRequest(
    val userInput : String,
    val character : String,
    val emotionState : String
)
