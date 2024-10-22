package com.iccas.zen.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.iccas.zen.data.dto.auth.request.LoginRequest
import com.iccas.zen.data.dto.auth.request.SignUpRequest
import com.iccas.zen.data.dto.auth.response.LoginResponse
import com.iccas.zen.data.remote.AuthApi
import com.iccas.zen.data.remote.RetrofitModule

class AuthViewModel : ViewModel() {

    private val _authentication = MutableStateFlow<LoginResponse?>(null)
    val authentication: StateFlow<LoginResponse?> = _authentication.asStateFlow()

    private val authApi: AuthApi = RetrofitModule.createService(AuthApi::class.java)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApi.authenticate(LoginRequest(email, password));
                _authentication.value = response.body()
                Log.d("AuthViewModel", "login: $response")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error login", e)
            }
        }
    }

    fun signUp(nickname: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApi.register(SignUpRequest(nickname, email, password));
                _authentication.value = response.body()
                Log.d("AuthViewModel", "sign up: $response")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error sign up", e)
            }
        }
    }
}
