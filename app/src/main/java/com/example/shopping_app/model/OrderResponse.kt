package com.example.shopping_app.model

data class OrderResponse(
    val id: Long? = null,
    val orderDate: String? = null,
    val totalAmount: Double,
    val orderItems: List<OrderItemResponse>
)
