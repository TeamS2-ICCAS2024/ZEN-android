package com.iccas.zen.presentation.report.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.data.dto.emotionDiary.response.DiaryEntry
import com.iccas.zen.data.dto.tetris.response.TetrisResultListResponse
import com.iccas.zen.data.remote.DiaryApi
import com.iccas.zen.data.remote.RetrofitModule
import com.iccas.zen.data.remote.TetrisApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import com.iccas.zen.data.dto.tetris.response.TetrisResultListResponse
import com.iccas.zen.data.dto.tetris.response.TetrisResultResponse

class ReportViewModel : ViewModel() {
    private val _tetrisResultList = MutableStateFlow<TetrisResultListResponse?>(null)
    val tetrisResultList: StateFlow<TetrisResultListResponse?> = _tetrisResultList.asStateFlow()

    private val _tetrisResult = MutableStateFlow<TetrisResultResponse?>(null)
    val tetrisResult: StateFlow<TetrisResultResponse?> = _tetrisResult.asStateFlow()
    
    private val _diaryList = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val diaryList: StateFlow<List<DiaryEntry>> = _diaryList.asStateFlow()

    private val tetrisApi: TetrisApi = RetrofitModule.createService(TetrisApi::class.java)
    private val diaryApi: DiaryApi = RetrofitModule.createService(DiaryApi::class.java)

    fun getTetrisResultList(year: Int, month: Int, userId: Long) {
        viewModelScope.launch {
            try {
                val response = tetrisApi.getTetrisResultList(year, month, userId)
                _tetrisResultList.value = response.body()
                Log.d("ReportViewModel", "get tetris result list: $response")
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error get tetris result list", e)
            }
        }
    }

    fun getTetrisResult(gameId: Long) {
        viewModelScope.launch {
            try {
                val response = tetrisApi.getTetrisResult(gameId);
                _tetrisResult.value = response.body()
                Log.d("ReportViewModel", "get tetris result: $response")
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error get tetris result", e)
            }
        }
    }
}

    fun getDiaryList() {
        viewModelScope.launch {
            try {
                val response = diaryApi.getDiaryList()
                if (response.isSuccessful) {
                    val diaryListResponse = response.body()
                    if (diaryListResponse != null && diaryListResponse.status == 200) {
                        _diaryList.value = diaryListResponse.data
                    } else {
                        Log.e("ReportViewModel", "Failed to load diary list: ${diaryListResponse?.message}")
                    }
                } else {
                    Log.e("ReportViewModel", "Error response code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error getting diary list", e)
            }
        }
    }
}
