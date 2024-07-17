package com.iccas.zen.presentation.yoga

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R
import com.iccas.zen.ui.theme.Green30

@Composable
fun ResultContent(onDismiss: () -> Unit, leafCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Result",
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            YogaResultItem(R.drawable.yoga_tree_pose, "Tree Pose", leafCount, 0)
            YogaResultItem(R.drawable.yoga_bridge_pose, "Bridge Pose", leafCount, 1)
            YogaResultItem(R.drawable.yoga_cat_cow_pose, "Cow Pose", leafCount, 2)
            YogaResultItem(R.drawable.yoga_child_pose, "Child Pose", leafCount, 3)
            YogaResultItem(R.drawable.yoga_cobra_pose, "Cobra Pose", leafCount, 4)
        }
        Spacer(modifier = Modifier.height(30.dp))

        Row() {
            Text(
                text = "+ $leafCount Leaf",
                fontSize = 25.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                color = Green30
            )
        }
        Spacer(modifier = Modifier.height(7.dp))

        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = Green30)
        ) {
            Text("Return to Home", color = Color.White, fontSize = 24.sp)
        }
    }
}

@Composable
fun YogaResultItem(
    poseImgId: Int,
    poseName: String,
    leafCount: Int,
    index: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = poseImgId),
                contentDescription = poseName,
                modifier = Modifier.size(38.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = poseName,
                fontSize = 22.sp,
                color = Color.Black
            )
        }

        Image(
            painter = painterResource(
                id = if (index < leafCount) {
                    R.drawable.yoga_leaf_filled
                } else {
                    R.drawable.yoga_leaf_unfilled
                }
            ),
            contentDescription = "Leaf Icon",
            modifier = Modifier.size(35.dp)
        )
    }
}
