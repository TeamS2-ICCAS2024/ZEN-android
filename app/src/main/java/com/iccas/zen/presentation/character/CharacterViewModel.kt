package com.iccas.zen.presentation.character

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.presentation.components.RetrofitInstance
import com.iccas.zen.presentation.components.UserResponse
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    var user by mutableStateOf<UserResponse?>(null)
        private set

    init {
        fetchUser()
    }

    public fun fetchUser() {
        viewModelScope.launch {
            try {
                user = RetrofitInstance.api.getUser()
                Log.d("CharacterViewModel", "User fetched successfully: $user")
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Error fetching user", e)
            }
        }
    }
}