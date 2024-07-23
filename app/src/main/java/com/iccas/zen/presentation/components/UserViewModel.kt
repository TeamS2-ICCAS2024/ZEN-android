package com.iccas.zen.presentation.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.data.dto.user.response.UserResponse
import com.iccas.zen.data.dto.user.response.testResult
import com.iccas.zen.data.remote.RetrofitModule
import com.iccas.zen.data.remote.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userApi: UserApi = RetrofitModule.createService(UserApi::class.java)
    var user by mutableStateOf<UserResponse?>(null)
        private set

    private val _selfTestResults = MutableStateFlow<List<testResult>>(emptyList())
    val selfTestResults: StateFlow<List<testResult>> get() = _selfTestResults

    init {
        getSelfTestResults()

    }


    fun addLeaf(leaf: Int) {
        viewModelScope.launch {
            try {
                userApi.addLeaf(leaf)
            } catch (e: Exception) {
            }
        }
    }

    fun postscore(score: Int) {
        viewModelScope.launch {
            try {
                userApi.postscore(score)
            } catch (e: Exception) {
            }
        }
    }

    private fun getSelfTestResults() {
        viewModelScope.launch {
            try {
                val results = userApi.getSelfTest()
                _selfTestResults.value = results
                // 파싱 로그
                Log.d("CommonViewModel", "Self test results fetched successfully: $results")
            } catch (e: Exception) {
                // 에러 처리
                Log.e("CommonViewModel", "Error fetching self test results", e)
            }
        }
    }

}