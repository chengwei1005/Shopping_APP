package com.example.shopping_app.network

import com.example.shopping_app.model.LoginRequest
import com.example.shopping_app.model.LoginResponse
import com.example.shopping_app.model.ProductResponse
import com.example.shopping_app.model.RegisterRequest
import com.example.shopping_app.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

}