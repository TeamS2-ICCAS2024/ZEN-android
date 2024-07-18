package com.iccas.zen.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.iccas.zen.presentation.auth.authComponents.AuthButton
import com.iccas.zen.presentation.auth.authComponents.UserInfoInputBox
import com.iccas.zen.presentation.components.BasicBackground
import com.iccas.zen.ui.theme.Brown40
@Composable
fun LoginScreen(navController: NavController) {
    BasicBackground {
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }

        val authViewModel: AuthViewModel = viewModel()
        val authentication by authViewModel.authentication.collectAsState()
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                UserInfoInputBox(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label ="email",
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(10.dp))

                UserInfoInputBox(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = "password",
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(100.dp))

                AuthButton(
                    onClick = {
                        if (emailState.value == "") {
                            Toast.makeText(context, "Please enter your email!", Toast.LENGTH_SHORT)
                                .show()
                        } else if (passwordState.value == "") {
                            Toast.makeText(
                                context,
                                "Please enter your password!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            authViewModel.login(emailState.value, passwordState.value)
                        }
                    },
                    buttonColor = Brown40,
                    text = "Login with email"
                )
            }
            authentication?.let { response ->
                LaunchedEffect(response) {
                    Log.d("login", "Status: ${response.status}")
                    Log.d("login", "Message: ${response.message}")
                    Log.d("login", "Data: ${response.data}")

                    if (response.status == 200) {
                        navController.navigate("char_main")
                    } else {
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
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
