package com.example.shopping_app.model

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val token: String, val message: String, val status: Int)
