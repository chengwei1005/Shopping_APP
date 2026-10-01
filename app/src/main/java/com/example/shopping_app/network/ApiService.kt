package com.example.shopping_app.network

import com.example.shopping_app.model.CartItemRequest
import com.example.shopping_app.model.CartItemResponse
import com.example.shopping_app.model.LoginRequest
import com.example.shopping_app.model.LoginResponse
import com.example.shopping_app.model.OrderResponse
import com.example.shopping_app.model.ProductResponse
import com.example.shopping_app.model.RegisterRequest
import com.example.shopping_app.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("api/products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("api/products/{id}")
    suspend fun getProductDetail(@Path("id") id: Long): ProductResponse

    @POST("api/cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body request: CartItemRequest
    ): Response<CartItemResponse>

    @DELETE("api/cart/remove/{id}")
    suspend fun removeFromCart(
        @Header("Authorization") token: String,
        @Path("id") cartItemId: Long
    ): Response<Unit>

    @GET("api/cart")
    suspend fun getAllCartItems(@Header("Authorization") token: String): Response<List<CartItemResponse>>

    @PUT("api/cart/update")
    suspend fun updateCartItemQuantity(
        @Header("Authorization") token: String,
        @Body request: CartItemRequest
    ): Response<CartItemResponse>

    @DELETE("api/cart/remove/{id}")
    suspend fun removeCartItem(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>

    @GET("api/orders")
    suspend fun getAllOrders(@Header("Authorization") token: String): Response<List<OrderResponse>>
}