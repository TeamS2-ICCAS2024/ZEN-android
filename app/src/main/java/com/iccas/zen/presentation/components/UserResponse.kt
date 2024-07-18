package com.iccas.zen.presentation.components

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class UserResponse(
    val id: Int,
    val nickname: String,
    val leaf: Int,
    val background_id: Int
)

data class ChangeBackgroundResponse(
    val result: String,
)

interface ApiService {
    @GET("user")
    suspend fun getUser(): UserResponse

    @POST("background")
    suspend fun changeBackground(@Query("background_id") backgroundId: Int): ChangeBackgroundResponse
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}