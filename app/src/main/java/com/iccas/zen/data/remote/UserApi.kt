package com.iccas.zen.data.remote

import com.iccas.zen.data.dto.user.response.ChangeBackgroundResponse
import com.iccas.zen.data.dto.user.response.UserResponse
import com.iccas.zen.data.dto.user.response.testResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @GET("/api/v1/user")
    suspend fun getUser(): UserResponse

    @POST("api/v1/background")
    suspend fun changeBackground(@Query("background_id") backgroundId: Int)

    @POST("api/v1/leaf")
    suspend fun addLeaf(@Query("leaf") leaf: Int)

    @POST("api/v1/selftest")
    suspend fun postscore(@Query("score") score: Int)

    @GET("api/v1/selftest")
    suspend fun getSelfTest(): List<testResult>
}