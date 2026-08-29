package com.example.shopping_app.controller

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.model.ProductResponse
import com.example.shopping_app.network.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.shopping_app.network.ApiClient

class ProductController : ViewModel() {

    var productList by mutableStateOf<List<ProductResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = ApiClient.retrofitService.getAllProducts()
                productList = response
            } catch (e: Exception) {
                Log.e("API_ERROR", "Fetch products failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}