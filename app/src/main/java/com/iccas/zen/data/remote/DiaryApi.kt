package com.iccas.zen.data.remote

import com.iccas.zen.data.dto.auth.request.EmotionDiaryPostRequest
import com.iccas.zen.data.dto.auth.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DiaryApi {
    @POST("/api/v1/emotion-diary")
    suspend fun postEmotionDiary(@Body emotionDiaryPostRequest: EmotionDiaryPostRequest): ApiResponse
}