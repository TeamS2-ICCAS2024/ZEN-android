package com.iccas.zen.presentation.yoga

import com.iccas.zen.R

data class YogaPose(
    val name: String,
    val poseImgId: Int,
    val durationSeconds: Int
)

val yogaPoses = listOf(
    YogaPose("Bend Pose", R.drawable.standing_forward_bend, 3),
    YogaPose("Bridge Pose", R.drawable.bridge_pose, 3),
    YogaPose("Cow Pose", R.drawable.cat_cow_pose, 3),
    YogaPose("Child Pose", R.drawable.child, 3),
    YogaPose("Corpse Pose", R.drawable.corpose, 3)
)
