package com.iccas.zen.presentation.report.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.iccas.zen.data.remote.RetrofitModule
import com.iccas.zen.data.remote.TetrisApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.iccas.zen.data.dto.tetris.response.TetrisResultListResponse

class ReportViewModel : ViewModel() {
    private val _tetrisResultList = MutableStateFlow<TetrisResultListResponse?>(null)
    val tetrisResultList: StateFlow<TetrisResultListResponse?> = _tetrisResultList.asStateFlow()

    private val tetrisApi: TetrisApi = RetrofitModule.createService(TetrisApi::class.java)

    fun getTetrisResultList(year: Int, month: Int, userId: Long) {
        viewModelScope.launch {
            try {
                val response = tetrisApi.getTetrisResultList(year, month, userId);
                _tetrisResultList.value = response.body()
                Log.d("ReportViewModel", "get tetris result list: $response")
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error get tetris result list", e)
            }
        }
    }
}