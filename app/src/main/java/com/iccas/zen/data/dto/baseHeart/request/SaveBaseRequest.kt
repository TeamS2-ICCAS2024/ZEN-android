package com.iccas.zen.data.dto.baseHeart.request

data class SaveBaseRequest(
    val userId: Long,
    val baseHeart: Int,
    val measureTime: String
)
