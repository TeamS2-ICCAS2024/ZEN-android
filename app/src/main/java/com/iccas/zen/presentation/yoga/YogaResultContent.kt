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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iccas.zen.R

@Composable
fun ResultContent(onDismiss: () -> Unit) {
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
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            YogaResultItem(R.drawable.easy_pose, "Crow Pose")
            YogaResultItem(R.drawable.bridge_pose, "Pigeon Pose")
            YogaResultItem(R.drawable.cat_cow_pose, "Triangle Pose")
            YogaResultItem(R.drawable.standing_forward_bend, "Plough Pose")
            YogaResultItem(R.drawable.child_pose, "Plank Pose")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "+ 30 Leaf",
            fontSize = 30.sp,
            color = Color.Green
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD3C4D1))
        ) {
            Text("Return to Home", color = Color.Black, fontSize = 24.sp)
        }
    }
}

@Composable
fun YogaResultItem(
    poseImgId: Int,
    poseName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = poseImgId),
            contentDescription = poseName,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = poseName, fontSize = 24.sp, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.leaf),
            contentDescription = "Leaf Icon",
            modifier = Modifier.size(40.dp)
        )
    }
}
