package com.example.shopping_app.controller


import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.model.LoginRequest
import com.example.shopping_app.model.RegisterRequest
import com.example.shopping_app.network.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginController : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf(value = "")
    val isPasswordMismatch: Boolean
        get() = password != confirmPassword

    var isLoading by mutableStateOf(false)

    fun login(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        println("DEBUG: 準備傳送到後端的資料 -> Email: $email, Password: $password")
        viewModelScope.launch {
            isLoading = true
            try {
                // 建立 Retrofit 實例 (建議以後移到單例物件中)
                val apiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/") // 模擬器連線位址
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)

                val request = LoginRequest(email, password)
                val response = apiService.login(request)

                if (response.status == 200) {
                    // 成功：回傳 Token 給 UI
                    onSuccess(response.token)
                } else {
                    // 失敗：回傳錯誤訊息
                    onFailure(response.message ?: "登入失敗")
                }
            } catch (e: Exception) {
                // 網路錯誤
                onFailure("連線異常: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun register(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val apiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/") // 模擬器連線位址
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)

                val request = RegisterRequest(name, email, password)
                val response = apiService.register(request)

                if (response.status == 200) {
                    // 成功：回傳 Token 給 UI
                    onSuccess(response.token)
                } else {
                    // 失敗：回傳錯誤訊息
                    onFailure(response.message ?: "登入失敗")
                }
            } catch (e: Exception) {
                // 網路錯誤
                onFailure("連線異常: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}