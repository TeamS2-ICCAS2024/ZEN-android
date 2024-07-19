package com.iccas.zen.data.dto.baseHeart.request

import java.time.LocalDateTime

data class SaveBaseRequest(
    val userId: Long,
    val baseHeart: Int,
    val measureTime: LocalDateTime
)
