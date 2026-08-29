package com.example.shopping_app.controller

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.model.CartItemRequest
import com.example.shopping_app.model.CartItemResponse
import com.example.shopping_app.network.ApiClient
import com.example.shopping_app.network.ApiService
import com.example.shopping_app.token.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartController(application: Application) : AndroidViewModel(application) {
    private val tokenManager = TokenManager(application)
    var isLoading by mutableStateOf(false)
        private set

    var cartList by mutableStateOf<List<CartItemResponse>>(emptyList())
        private set

    val subtotalAmount: Double
        get() = cartList.sumOf { it.unitPrice * it.quantity }

    fun addToCart(productId: Long, quantity: Int = 1) {
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

                val request = CartItemRequest(productId, quantity)
                val response = ApiClient.retrofitService.addToCart(authHeader, request)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "加入成功: ${response.body()?.productName}")
                    getAllCartItems()
                } else {
                    Log.e("API_ERROR", "加入失敗: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "add products failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getAllCartItems() {
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
                val response = ApiClient.retrofitService.getAllCartItems(authHeader)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "取得購物車商品成功: ${response.body()?.toString()}")
                    response.body()?.let { items ->
                        cartList = items
                    }
                } else {
                    Log.e("API_ERROR", "取得購物車商品失敗: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "get products failed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun updateItemQuantity(cartItemId: Long, isAdd: Boolean) {
        val currentItem = cartList.find { it.cartItemId == cartItemId } ?: return
        val newQuantity = if (isAdd) currentItem.quantity + 1 else currentItem.quantity - 1

        cartList = if (newQuantity < 1) {
            cartList.filter { it.cartItemId != cartItemId }
        } else {
            cartList.map { item ->
                if (item.cartItemId == cartItemId) item.copy(quantity = newQuantity) else item
            }
        }

        viewModelScope.launch {
            try {
                // 拿出 Token
                val myToken = tokenManager.getToken() ?: return@launch
                val authHeader = "Bearer $myToken"

                if (newQuantity < 1) {
                    val response = ApiClient.retrofitService.removeCartItem(authHeader, cartItemId)

                    if (!response.isSuccessful) {
                        getAllCartItems()
                    }
                } else {
                    val request = CartItemRequest(
                        cartItemId = currentItem.cartItemId,
                        quantity = newQuantity
                    )

                    val response =
                        ApiClient.retrofitService.updateCartItemQuantity(authHeader, request)

                    if (!response.isSuccessful) {
                        getAllCartItems()
                    }
                }
            } catch (e: Exception) {
                getAllCartItems()
            }
        }
    }
}