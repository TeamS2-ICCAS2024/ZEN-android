package com.iccas.zen.data.dto.auth.response

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: AuthData
)
data class AuthData(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)