package com.example.shopping_app.model

data class CartItemResponse(
    val cartItemId: Long,
    val productId: String,
    val productName: String,
    val unitPrice: Double,
    val quantity: Int,
    val subTotal: Double,
    val imageUrl: String?
)
