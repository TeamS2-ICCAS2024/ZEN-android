package com.iccas.zen.presentation.character

import com.iccas.zen.R

data class Character(
    val name: String,
    val charImgId: Int,
    val required_leaf: Int
)

val Characters = listOf(
    Character("MOZZI", R.drawable.char_mozzi1, 0),
    Character("MOZZI", R.drawable.char_mozzi2, 100),
    Character("MOZZI", R.drawable.char_mozzi3, 200),
    Character("BAO", R.drawable.char_bao1, 300),
    Character("BAO", R.drawable.char_bao2, 400),
    Character("BAO", R.drawable.char_bao3, 500),
    Character("SKY", R.drawable.char_sky1, 600),
    Character("SKY", R.drawable.char_sky2, 700),
    Character("SKY", R.drawable.char_sky3, 800),
)
