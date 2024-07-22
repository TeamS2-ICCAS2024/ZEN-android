package com.iccas.zen.data.dto.emotionDiary.response

import com.google.gson.annotations.SerializedName

data class DiaryDetailResponse(
    val status: Int,
    var message: String,
    var data: DiaryDetailData
)
data class DiaryDetailData(
    @SerializedName("when") val whenDetail: String, // "when"을 Kotlin 키워드와 충돌하지 않도록 변경
    val emotion: String,
    val summary: String,
    val solution: String
)
