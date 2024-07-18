package com.iccas.zen.data.remote
import com.iccas.zen.data.dto.auth.request.LoginRequest
import com.iccas.zen.data.dto.auth.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/v1/auth/authenticate")
    suspend fun authenticate(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
