package com.example.shopping_app.model

data class RegisterRequest(val name: String, val email: String, val password: String)

data class RegisterResponse(val token: String, val message: String, val status: Int)