package com.iccas.zen.data.remote

import com.iccas.zen.data.dto.emotionDiary.request.EmotionDiaryPostRequest
import com.iccas.zen.data.dto.ApiResponse
import com.iccas.zen.data.dto.emotionDiary.response.DiaryDetailResponse
import com.iccas.zen.data.dto.emotionDiary.response.DiaryListGetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryApi {

    @POST("/api/v1/emotion-diary")
    suspend fun postEmotionDiary(@Body emotionDiaryPostRequest: EmotionDiaryPostRequest): ApiResponse

    @GET("/api/v1/diary-list")
    suspend fun getDiaryList(): Response<DiaryListGetResponse>

    @GET("/api/v1/emotion-diary/{emotionDiaryId}")
    suspend fun getDiaryDetail(@Path("emotionDiaryId") emotionDiaryId: Long): Response<DiaryDetailResponse>
}