package com.iccas.zen.data.dto.tetris.response

import com.iccas.zen.data.dto.baseHeart.response.BaseData

data class TetrisResultResponse(
    val status: Int,
    var message: String,
    var data: TetrisResult
)

data class TetrisResult (
    val id: Long,
    val score: Int,
    val level: Int,
    val lives: Int,
    val playTime: String,
    val baseHeartRate: BaseData,
    val heartRateList: List<Int>,
    val averageHearRate: Float,
    val gameStartTime: String
)