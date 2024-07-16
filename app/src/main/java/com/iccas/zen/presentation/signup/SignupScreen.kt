package com.iccas.zen.presentation.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    BasicBackground {
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }

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
                TextField(
                    shape = RoundedCornerShape(50.dp),
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White // 내부 배경색 변경
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(50.dp),
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White // 내부 배경색 변경
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(100.dp))

                Button(
                    onClick = { navController.navigate("tetris_game") },
                    colors = ButtonDefaults.buttonColors(containerColor = Brown40),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Login with email", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {
    val nicknameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }

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
                TextField(
                    value = nicknameState.value,
                    onValueChange = { nicknameState.value = it },
                    label = { Text("Nickname") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White // 내부 배경색 변경
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White // 내부 배경색 변경
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = confirmPasswordState.value,
                    onValueChange = { confirmPasswordState.value = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White // 내부 배경색 변경
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("welcome") },
                    colors = ButtonDefaults.buttonColors(containerColor = Brown40),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // 버튼 높이 설정
                ) {
                    Text("Sign up", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
