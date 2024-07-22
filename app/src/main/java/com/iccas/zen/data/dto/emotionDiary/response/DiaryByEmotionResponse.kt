package com.iccas.zen.data.dto.emotionDiary.response

data class DiaryByEmotionResponse (
    val status: Int,
    val message: String,
    val data: List<String>
)