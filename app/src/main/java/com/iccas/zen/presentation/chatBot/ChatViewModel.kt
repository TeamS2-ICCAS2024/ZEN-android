package com.iccas.zen.presentation.chatBot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iccas.zen.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val client = HttpClient()

    fun sendMessage(text: String) {
        _messages.value = _messages.value + Message(text, true)
        viewModelScope.launch {
            val response = getResponseFromApi(text)
            _messages.value = _messages.value + Message(response, false)
        }
    }

    @OptIn(InternalAPI::class)
    private suspend fun getResponseFromApi(text: String): String {
        return try {
            val response: HttpResponse = client.post("https://api.openai.com/v1/chat/completions") {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    append("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
                }
                body = """
                {
                    "model": "gpt-3.5-turbo",
                    "messages": [{"role": "user", "content": "$text"}],
                    "max_tokens": 150,
                    "temperature": 1.0
                }
            """.trimIndent()
            }
            val responseBody: String = response.body()
            Log.d("ChatViewModel", "API Response: $responseBody")
            parseApiResponse(responseBody)
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Request failed: ${e.message}")
            "Request failed: ${e.message}"
        }
    }




    private fun parseApiResponse(responseBody: String): String {
        val json = kotlinx.serialization.json.Json.parseToJsonElement(responseBody)
        val messageContent = json.jsonObject["choices"]?.jsonArray?.get(0)?.jsonObject?.get("message")?.jsonObject?.get("content")?.jsonPrimitive?.content
        return messageContent ?: "Error parsing response"
    }

}
