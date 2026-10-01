package com.example.shopping_app.controller

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.model.OrderResponse
import com.example.shopping_app.network.ApiClient
import com.example.shopping_app.token.TokenManager
import kotlinx.coroutines.launch

class OrderController(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    var isLoading by mutableStateOf(false)
        private set

    var orderList by mutableStateOf<List<OrderResponse>>(emptyList())

    fun getTotalOrder() {
        viewModelScope.launch {
            isLoading = true
            try {
                val myToken = tokenManager.getToken()
                if (myToken == null) {
                    Log.e("API_ERROR", "User do not login， don't have Token！")
                    isLoading = false
                    return@launch
                }
                val authHeader = "Bearer $myToken"
                val response = ApiClient.retrofitService.getAllOrders(authHeader)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Get Order successful: ${response.body()?.toString()}")
                    val fetchedOrders = response.body() ?: emptyList()
                    orderList = fetchedOrders
                } else {
                    Log.e("API_ERROR", "Get order failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Get orders failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}