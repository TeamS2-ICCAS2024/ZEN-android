package com.iccas.zen.data.remote

import com.iccas.zen.data.dto.ApiResponse
import com.iccas.zen.data.dto.tetris.request.TetrisResultRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface TetrisApi {
    @POST("api/v1/tetris/save")
    suspend fun saveTetrisResult(@Body tetrisResultRequest: TetrisResultRequest): ApiResponse

}

