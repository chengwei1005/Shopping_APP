package com.example.shopping_app.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val id: String,
    val name: String,
    val price: Double,
    val stock: Int,
    val description: String,
    val imageUrl: String?,
    val type: String,
)
