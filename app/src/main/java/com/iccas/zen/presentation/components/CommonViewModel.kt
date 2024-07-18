package com.iccas.zen.presentation.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.data.dto.user.response.UserResponse
import com.iccas.zen.data.remote.RetrofitModule
import com.iccas.zen.data.remote.UserApi
import kotlinx.coroutines.launch

class CommonViewModel : ViewModel() {
    private val userApi: UserApi = RetrofitModule.createService(UserApi::class.java)
    var user by mutableStateOf<UserResponse?>(null)
        private set

    init {
    }


    fun changeBackground(backgroundId: Int) {
        viewModelScope.launch {
            try {
                userApi.changeBackground(backgroundId)
                Log.d("CharacterViewModel", "Background changed successfully")
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error changing background", e)
            }
        }
    }

    fun addLeaf(leaf: Int) {
        viewModelScope.launch {
            try {
                userApi.addLeaf(leaf)
            } catch (e: Exception) {
            }
        }
    }

}