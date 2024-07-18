package com.iccas.zen.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iccas.zen.R
import com.iccas.zen.presentation.components.BasicBackground
import com.iccas.zen.ui.theme.Brown40

@Composable
fun WelcomeScreen(navController: NavController) {
    BasicBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zen_brown_logo), // 테이프가 붙은 종이 이미지
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp) // 높이를 더 키움
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(200.dp))

                Button(
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, color = Brown40),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // 버튼 높이 설정
                ) {
                    Text("Login with email", color = Brown40, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Google 로그인 로직 추가 */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, color = Brown40),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // 버튼 높이 설정
                ) {
                    Text("Continue with Google", color = Brown40, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Don't you have an account yet?", color = Brown40, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                TextButton(
                    onClick = { navController.navigate("signup") },
                    colors = ButtonDefaults.buttonColors(containerColor = Brown40),
                    border = BorderStroke(2.dp, color = Brown40),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // 버튼 높이 설정
                ) {
                    Text("Sign up with email", color = Color.White, fontSize = 20.sp)
                }
            }

            Text(
                text = "copyright © S2",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}
