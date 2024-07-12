package com.iccas.zen.presentation.heart.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        val average = _averageHeartRate.value ?: return
        val currentRate = _heartRates.value.filter { it > 0 }.lastOrNull() ?: return

        if (currentRate > average + 10) {
            _isHeartRateHigh.value = true
        } else if (currentRate <= average + 5) {
            _isHeartRateHigh.value = false
        }
    }
}
