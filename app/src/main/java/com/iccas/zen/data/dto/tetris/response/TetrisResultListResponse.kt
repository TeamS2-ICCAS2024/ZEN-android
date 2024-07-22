package com.iccas.zen.data.dto.tetris.response

data class TetrisResultListResponse(
    val status: Int,
    val message: String,
    val data: List<TetrisResultListData>
)

data class TetrisResultListData(
    val id: Long,
    val lives: Int,
    val startTime: String
)
