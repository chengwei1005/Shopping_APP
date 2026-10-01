package com.example.shopping_app.model

data class OrderItemResponse(
    val id: Long,
    val product: ProductResponse,
    val quantity: Int,
    val price: Double
)
