package com.iccas.zen.data.dto.auth.request

data class SignUpRequest(
    val nickname: String,
    val email: String,
    val password: String
)
