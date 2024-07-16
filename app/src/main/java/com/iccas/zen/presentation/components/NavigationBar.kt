package com.iccas.zen.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        elevation = 0.dp,
        modifier = Modifier.height(50.dp),  // 높이를 명시적으로 설정
        contentPadding = PaddingValues(start = 0.dp, end = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationBarItem(Icons.Default.Home, "Main") {
                navController.navigate("char_main")
            }
            NavigationBarItem(Icons.Default.Home, "Game") {
                navController.navigate("game_select")
            }
            NavigationBarItem(Icons.Default.Home, "Diary") {
                // Diary 화면으로 이동하는 로직
            }
            NavigationBarItem(Icons.Default.Home, "Collection") {
                // Collection 화면으로 이동하는 로직
            }
            NavigationBarItem(Icons.Default.Home, "Report") {
                // Report 화면으로 이동하는 로직
            }
        }
    }
}

@Composable
fun NavigationBarItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 8.dp)  // 패딩을 넉넉히 설정
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(24.dp)  // 아이콘 크기를 작게 설정
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.Black)
        }
        Text(text = label, fontSize = 10.sp, color = Color.Black)
    }
}