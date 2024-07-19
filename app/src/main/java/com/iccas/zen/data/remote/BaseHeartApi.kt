package com.iccas.zen.data.remote

import com.iccas.zen.data.dto.baseHeart.request.SaveBaseRequest
import com.iccas.zen.data.dto.baseHeart.response.SaveBaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BaseHeartApi {
    @POST("/api/v1/base/save")
    suspend fun saveBase(@Body saveBaseRequest: SaveBaseRequest): Response<SaveBaseResponse>

    @GET("/api/v1/base/latest/user/{userId}")
    suspend fun getLatestBase(@Path("userId") userId: Long): Response<SaveBaseResponse>
}