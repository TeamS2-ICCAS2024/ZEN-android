package com.iccas.zen.data.remote

import com.iccas.zen.data.dto.ApiResponse
import com.iccas.zen.data.dto.tetris.request.TetrisResultRequest
import com.iccas.zen.data.dto.tetris.response.TetrisResultListResponse
import com.iccas.zen.data.dto.tetris.response.TetrisResultResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TetrisApi {
    @POST("api/v1/tetris/save")
    suspend fun saveTetrisResult(@Body tetrisResultRequest: TetrisResultRequest): ApiResponse

    @GET("api/v1/tetris/list/year/{year}/month/{month}/user/{userId}")
    suspend fun getTetrisResultList(@Path("year") year: Int, @Path("month") month: Int, @Path("userId") userId: Long): Response<TetrisResultListResponse>
    @GET("api/v1/tetris/game/{gameId}")
    suspend fun getTetrisResult(@Path("gameId") gameId: Long): Response<TetrisResultResponse>
}

