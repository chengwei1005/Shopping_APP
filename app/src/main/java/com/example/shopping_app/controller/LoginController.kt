package com.example.shopping_app.controller


import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.model.LoginRequest
import com.example.shopping_app.model.RegisterRequest
import com.example.shopping_app.network.ApiClient
import com.example.shopping_app.network.ApiService
import com.example.shopping_app.token.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginController(application: Application) : AndroidViewModel(application) {
    private val tokenManager = TokenManager(application)

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf(value = "")
    val isPasswordMismatch: Boolean
        get() = password != confirmPassword

    var isLoading by mutableStateOf(false)


    fun login(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val request = LoginRequest(email, password)
                val response = ApiClient.retrofitService.login(request)

                if (response.status == 200) {
                    tokenManager.saveToken(response.token)
                    onSuccess(response.token)
                } else {
                    onFailure(response.message ?: "Login Failed")
                }
            } catch (e: Exception) {
                onFailure("Connect Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun register(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {

                val request = RegisterRequest(name, email, password)
                val response = ApiClient.retrofitService.register(request)

                if (response.status == 200) {
                    onSuccess(response.token)
                } else {
                    onFailure(response.message ?: "Login Failed")
                }
            } catch (e: Exception) {
                onFailure("Connect Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}