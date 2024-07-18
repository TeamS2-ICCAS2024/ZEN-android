package com.iccas.zen.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.presentation.auth.authComponents.AuthButton
import com.iccas.zen.presentation.auth.authComponents.UserInfoInputBox

import com.iccas.zen.presentation.components.BasicBackground
import com.iccas.zen.ui.theme.Brown40

@Composable
fun SignupScreen(navController: NavController) {
    val nicknameState = remember { mutableStateOf("") }
    val emaiState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }

    val authViewModel: AuthViewModel = viewModel()
    val authentication by authViewModel.authentication.collectAsState()
    val context = LocalContext.current

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
                UserInfoInputBox(
                    value = nicknameState.value,
                    onValueChange = { nicknameState.value = it },
                    label = "nickname",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(10.dp))

                UserInfoInputBox(
                    value = emaiState.value,
                    onValueChange = { emaiState.value = it },
                    label = "email",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(10.dp))

                UserInfoInputBox(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = "password",
                    keyboardType = KeyboardType.Password
                )
                Spacer(modifier = Modifier.height(10.dp))

                UserInfoInputBox(
                    value = confirmPasswordState.value,
                    onValueChange = { confirmPasswordState.value = it },
                    label = "confirm password",
                    keyboardType = KeyboardType.Password
                )
                Spacer(modifier = Modifier.height(50.dp))

                AuthButton(
                    onClick = {
                        if (nicknameState.value == "") {
                            Toast.makeText(context, "Please enter your nickname!", Toast.LENGTH_SHORT).show()
                        } else if (emaiState.value =="") {
                            Toast.makeText(context, "Please enter your email!", Toast.LENGTH_SHORT).show()
                        } else if (passwordState.value =="") {
                            Toast.makeText(context, "Please enter your password!", Toast.LENGTH_SHORT).show()
                        } else if (passwordState.value != confirmPasswordState.value){
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        } else {
                            authViewModel.signUp(nicknameState.value, emaiState.value, passwordState.value)
                        }
                    },
                    buttonColor = Brown40,
                    text = "Sign up"
                )
            }
            authentication?.let { response ->
                LaunchedEffect(response) {
                    Log.d("sign up", "Status: ${response.status}")
                    Log.d("sign up", "Message: ${response.message}")
                    Log.d("sign up", "Data: ${response.data}")

                    if (response.status == 200) {
                        navController.navigate("char_main")
                    } else {
                        Toast.makeText(context, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }
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
