package com.iccas.zen.presentation.tetris.logic

import androidx.compose.ui.geometry.Offset

data class Brick(val location: Offset = Offset(0F, 0F)) {
    companion object {
        fun of(offsetList: List<Offset>) = offsetList.map { Brick(it) }

        fun of(spirit: Spirit) = of(spirit.location)

        fun of(xRange: IntRange, yRange: IntRange) =
            of(mutableListOf<Offset>().apply {
                xRange.forEach { x ->
                    yRange.forEach { y ->
                        this += Offset(x.toFloat(), y.toFloat())
                    }
                }
            }
        )
    }

    fun offsetBy(step: Pair<Int, Int>) =
        copy(location = Offset(location.x + step.first, location.y + step.second))
}