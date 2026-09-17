package com.example.shopping_app.model

data class CartItemRequest(
    val productId: String,
    val cartItemId: Long? = null,
    val quantity: Int
)