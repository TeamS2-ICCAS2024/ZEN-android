package com.iccas.zen.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iccas.zen.R

@Composable
fun BasicBackground2(
    content: @Composable () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.basic_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp, horizontal = 25.dp)
        ) {
            content()
        }
    }
}

@Composable
fun BasicBackgroundWithNavBar(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BasicBackground2 {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.zen_brown_logo),
                        contentDescription = null,
                        modifier = Modifier.width(65.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.weight(1f))  // Content를 화면 중앙에 배치하도록 유도
                content()
            }
        }
        Column(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavigationBar(navController = navController)
        }
    }
}