package com.iccas.zen.data.dto.user.response

data class UserResponse(
    val id: Int,
    val nickname: String,
    val leaf: Int,
    val background_id: Int,
    val lastTestAt: String
)

data class testResult(
    val id: Int,
    val userId: Int,
    val score: Int,
    val createdAt: String,
)