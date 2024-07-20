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
import androidx.compose.ui.unit.Dp
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
            NavigationBarItem(R.drawable.main_icon, "Main",iconSize = 40.dp ) {
                navController.navigate("char_main")
            }
            NavigationBarItem(R.drawable.game_icon, "Game",iconSize = 40.dp) {
                navController.navigate("game_select")
            }
            NavigationBarItem(R.drawable.diary_icon, "Diary", iconSize = 40.dp) {
                // 필요한 매개변수 설정
                val characterImageRes = R.drawable.char_mozzi1 // 원하는 이미지 리소스 ID
                val characterDescription = "Mozzi" // 원하는 캐릭터 설명

                // 올바른 네비게이션 경로와 매개변수를 포함하여 navigate 호출
                navController.navigate("emotion_diary")
            }
            NavigationBarItem(R.drawable.report_icon, "Report", iconSize = 40.dp) {
                navController.navigate("report")
            }
            NavigationBarItem(R.drawable.collect_icon, "Collection", iconSize = 50.dp) {
                navController.navigate("collection")
            }
        }
    }
}

@Composable
fun NavigationBarItem(
    iconResId: Int,
    label: String,
    iconSize:Dp,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 1.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(55.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(iconSize)
            )
        }
        Text(text = label, fontSize = 15.sp, color = Color.Black)
    }
}
