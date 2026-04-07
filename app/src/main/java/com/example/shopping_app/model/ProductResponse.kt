package com.example.shopping_app.model

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String? = null
)
