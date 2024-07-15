package com.cookandroid.myapplication

import androidx.annotation.DrawableRes

data class YogaPose(
    val name: String,
    @DrawableRes val imageRes: Int,
    val durationSeconds: Int
)

val yogaPoses = listOf(
    YogaPose("Bend Pose", R.drawable.standing_forward_bend, 3),
    YogaPose("Bridge Pose", R.drawable.bridge_pose, 100),
    YogaPose("Cow Pose", R.drawable.cat_cow_pose, 100),
    YogaPose("Child Pose", R.drawable.child, 100),
    YogaPose("Corpse Pose", R.drawable.corpose, 100)
)
