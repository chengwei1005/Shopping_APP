package com.example.shopping_app.network

import com.example.shopping_app.model.LoginRequest
import com.example.shopping_app.model.LoginResponse
import com.example.shopping_app.model.RegisterRequest
import com.example.shopping_app.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse
}