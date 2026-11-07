package com.example.shopping_app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.model.OrderResponse
import com.example.shopping_app.network.ApiClient
import com.example.shopping_app.token.TokenManager
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutViewModel(private val tokenManager: TokenManager) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var orderList by mutableStateOf<List<OrderResponse>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isCheckoutSuccess by mutableStateOf(false)
        private set

    fun checkout() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isCheckoutSuccess = false
            try {
                val myToken = tokenManager.getToken()
                if (myToken == null) {
                    Log.e("API_ERROR", "User do not login， don't have Token！")
                    errorMessage = "You don't log in. Please log in first."
                    isLoading = false
                    return@launch
                }
                val authHeader = "Bearer $myToken"
                val response = ApiClient.retrofitService.createOrder(authHeader)

                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Create order successful")
                    isCheckoutSuccess = true
                } else {
                    if (response.code() == 400) {
                        val errorStr = response.errorBody()?.string()
                        if (errorStr != null) {
                            val jsonObject = JSONObject(errorStr)
                            errorMessage = jsonObject.getString("message")
                            Log.e("API_ERROR", "Stock Error: $errorMessage")
                        } else {
                            errorMessage = "Checkout failed, Please check the cart"
                        }
                    } else {
                        errorMessage = "Server error, code：${response.code()}"
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Get orders failed: ${e.message}")
                errorMessage = "Internet connect error, please try again"
            } finally {
                isLoading = false
            }
        }
    }

    fun clearErrorMessage() {
        errorMessage = null
    }
}