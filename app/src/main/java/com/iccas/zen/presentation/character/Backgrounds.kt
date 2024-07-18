package com.iccas.zen.presentation.character

import com.iccas.zen.R

data class Background(
    val name: String,
    val bgId: Int,
)

val Backgrounds = listOf(
    Background("default", R.drawable.background1),
    Background("flowers", R.drawable.background2),
    Background("house", R.drawable.background3),
    Background("in house", R.drawable.background4),
)