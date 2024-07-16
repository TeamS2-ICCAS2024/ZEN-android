package com.iccas.zen.presentation.yoga

import com.iccas.zen.R

data class YogaPose(
    val name: String,
    val poseImgId: Int,
    val durationSeconds: Int
)

val yogaPoses = listOf(
    YogaPose("Tree Pose", R.drawable.yoga_tree_pose, 3),
    YogaPose("Bridge Pose", R.drawable.yoga_bridge_pose, 3),
    YogaPose("Cow Pose", R.drawable.yoga_cat_cow_pose, 3),
    YogaPose("Child Pose", R.drawable.yoga_child_pose, 3),
    YogaPose("Cobra Pose", R.drawable.yoga_cobra_pose, 3)
)
