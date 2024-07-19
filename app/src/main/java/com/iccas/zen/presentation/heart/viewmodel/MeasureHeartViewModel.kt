package com.iccas.zen.presentation.heart.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.data.dto.baseHeart.request.SaveBaseRequest
import com.iccas.zen.data.dto.baseHeart.response.SaveBaseResponse
import com.iccas.zen.data.remote.BaseHeartApi
import com.iccas.zen.data.remote.RetrofitModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.math.roundToInt

class MeasureHeartViewModel : ViewModel() {
    private val _heartRates = MutableStateFlow<List<Int>>(emptyList())
    val heartRates: StateFlow<List<Int>> = _heartRates

    private val _statusText = MutableStateFlow("Disconnected")
    val statusText: StateFlow<String> = _statusText

    private val _receivedData = MutableStateFlow("Waiting for data...")
    val receivedData: StateFlow<String> = _receivedData

    private val _averageHeartRate = MutableStateFlow<Int?>(null)
    val averageHeartRate: StateFlow<Int?> = _averageHeartRate

    private val _isHeartRateHigh = MutableStateFlow(false)
    val isHeartRateHigh: StateFlow<Boolean> = _isHeartRateHigh

    private val baseHeartApi: BaseHeartApi = RetrofitModule.createService(BaseHeartApi::class.java)

    private val _latestBaseData = MutableStateFlow<SaveBaseResponse?>(null)
    val latestBaseData: StateFlow<SaveBaseResponse?> = _latestBaseData.asStateFlow()

    fun updateHeartRate(heartRate: Int) {
        _heartRates.value += heartRate
        checkHeartRateThreshold()
    }

    fun calculateAverageHeartRate() {
        _averageHeartRate.value = _heartRates.value.filter { it > 0 }.average().roundToInt()
    }

    fun updateStatus(status: String) {
        _statusText.value = status
    }

    fun updateReceivedData(data: String) {
        _receivedData.value = data
    }

    fun clearHeartRates() {
        _heartRates.value = emptyList()
        _isHeartRateHigh.value = false
    }

    private fun checkHeartRateThreshold() {
        val average = _latestBaseData.value?.data?.baseHeart
        val currentRate = _heartRates.value.filter { it > 0 }.lastOrNull() ?: return

        if (average != null) {
            if (currentRate > average + 10) {
                _isHeartRateHigh.value = true
            } else if (currentRate <= average + 5) {
                _isHeartRateHigh.value = false
            }
        }
    }

    fun getLatestBase(userId: Long) {
        viewModelScope.launch {
            try {
                val response = baseHeartApi.getLatestBase(userId);
                if (response.code() == 200) {
                    _latestBaseData.value = response.body()
                } else {
                    _latestBaseData.value = SaveBaseResponse(
                        status = response.code(),
                        message = response.message(),
                        data = null
                    )
                }
                Log.d("MeasureHeartViewModel", "get latest base: $response")
            } catch (e: Exception) {
                Log.e("MeasureHeartViewModel", "Error get latest base", e)
            }
        }
    }

    fun saveBase(userId: Long, baseHeart: Int, measureTime: String) {
        viewModelScope.launch {
            try {
                val response = baseHeartApi.saveBase(SaveBaseRequest(userId, baseHeart, measureTime));
                if (response.code() == 200) {
                    _latestBaseData.value = response.body()
                } else {
                    _latestBaseData.value = SaveBaseResponse(
                        status = response.code(),
                        message = response.message(),
                        data = null
                    )
                }
                Log.d("MeasureHeartViewModel", "save base: $response")
            } catch (e: Exception) {
                Log.e("MeasureHeartViewModel", "Error save base", e)
            }
        }
    }

}
