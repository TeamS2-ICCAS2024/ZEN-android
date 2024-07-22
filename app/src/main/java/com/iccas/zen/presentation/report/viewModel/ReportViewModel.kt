package com.iccas.zen.presentation.report.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.data.dto.emotionDiary.response.DiaryEntry
import com.iccas.zen.data.dto.emotionDiary.response.DiaryDetailResponse
import com.iccas.zen.data.dto.tetris.response.TetrisResultListResponse
import com.iccas.zen.data.remote.DiaryApi
import com.iccas.zen.data.remote.RetrofitModule
import com.iccas.zen.data.remote.TetrisApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.iccas.zen.data.dto.tetris.response.TetrisResultResponse

class ReportViewModel : ViewModel() {
    private val _tetrisResultList = MutableStateFlow<TetrisResultListResponse?>(null)
    val tetrisResultList: StateFlow<TetrisResultListResponse?> = _tetrisResultList.asStateFlow()

    private val _tetrisResult = MutableStateFlow<TetrisResultResponse?>(null)
    val tetrisResult: StateFlow<TetrisResultResponse?> = _tetrisResult.asStateFlow()
    
    private val _diaryList = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val diaryList: StateFlow<List<DiaryEntry>> = _diaryList.asStateFlow()

    private val _diaryDetail = MutableStateFlow<DiaryDetailResponse?>(null)
    val diaryDetail: StateFlow<DiaryDetailResponse?> = _diaryDetail.asStateFlow()

    private val tetrisApi: TetrisApi = RetrofitModule.createService(TetrisApi::class.java)
    private val diaryApi: DiaryApi = RetrofitModule.createService(DiaryApi::class.java)

    fun getTetrisResultList(year: Int, month: Int, userId: Long) {
        viewModelScope.launch {
            try {
                val response = tetrisApi.getTetrisResultList(year, month, userId)
                if (response.isSuccessful) {
                    _tetrisResultList.value = response.body()
                    Log.d("ReportViewModel", "Tetris result list fetched successfully.")
                } else {
                    Log.e("ReportViewModel", "Failed to fetch tetris result list: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error fetching tetris result list", e)
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

    fun getDiaryList() {
        viewModelScope.launch {
            try {
                val response = diaryApi.getDiaryList()
                if (response.isSuccessful) {
                    val diaryListResponse = response.body()
                    if (diaryListResponse != null && diaryListResponse.status == 200) {
                        _diaryList.value = diaryListResponse.data
                        Log.d("ReportViewModel", "Diary list fetched successfully.")
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

    fun getDiaryDetail(emotionDiaryId: Long) {
        viewModelScope.launch {
            try {
                val response = diaryApi.getDiaryDetail(emotionDiaryId)
                if (response.isSuccessful) {
                    _diaryDetail.value = response.body()
                    Log.d("ReportViewModel"," getDiaryDetail 성공")
                } else {
                    Log.e("ReportViewModel", "Error response code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReportViewModel", "Error getting diary detail", e)
            }
        }
    }
}
