package com.example.shopping_app.model

data class CartItemRequest(
    val cartItemId: Long,
    val quantity: Int
)