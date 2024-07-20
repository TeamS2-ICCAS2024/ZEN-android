package com.iccas.zen.data.dto.tetris.request

data class TetrisResultRequest(
    val userId: Long,
    val heartRateList: List<Int>,
    val baseHeartId: Long,
    val level: Int,
    val score: Int,
    val lives: Int,
    val gameStartTime: String,
    val gameEndTime: String
)