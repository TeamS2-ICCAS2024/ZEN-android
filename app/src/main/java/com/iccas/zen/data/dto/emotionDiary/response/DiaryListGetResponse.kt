package com.iccas.zen.data.dto.emotionDiary.response

data class DiaryListGetResponse(
    val status: Int,
    val message: String,
    val data: List<DiaryEntry>
)

data class DiaryEntry(
    val character: String,
    val date: String,
    val emotionDiaryId: Long
)

