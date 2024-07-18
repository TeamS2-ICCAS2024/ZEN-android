package com.iccas.zen.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier
            .height(100.dp)
            .background(color = Color.Transparent)
            .padding(horizontal = 0.dp),
        contentColor = Color.Black,
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationBarItem(R.drawable.main_icon, "Main") {
                navController.navigate("char_main")
            }
            NavigationBarItem(R.drawable.game_icon, "Game") {
                navController.navigate("game_select")
            }
            NavigationBarItem(R.drawable.diary_icon, "Diary") {
                navController.navigate("select_emotion")
            }
            NavigationBarItem(R.drawable.report_icon, "Report") {
                navController.navigate("report")
            }
            NavigationBarItem(R.drawable.collect2_icon, "Collection") {
                navController.navigate("collection")
            }
        }
    }
}

@Composable
fun NavigationBarItem(
    iconResId: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(55.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(50.dp)
            )
        }
        Text(text = label, fontSize = 15.sp, color = Color.Black)
    }
}
