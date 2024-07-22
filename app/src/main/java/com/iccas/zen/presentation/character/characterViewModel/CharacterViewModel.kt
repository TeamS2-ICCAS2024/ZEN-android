package com.iccas.zen.presentation.character.characterViewModel

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

class CharacterViewModel : ViewModel() {
    private val userApi: UserApi = RetrofitModule.createService(UserApi::class.java)
    var user by mutableStateOf<UserResponse?>(null)
        private set

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                user = userApi.getUser();
                Log.d("CharacterViewModel", "User fetched successfully: $user")
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error fetching user", e)
            }
        }
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

}