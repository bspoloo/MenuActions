package com.example.menuactions.dataclasses

data class Product(
    val product_id: Int = 0,
    val name: String,
    val price: Double,
    val description: String,
    val created_at: String,
    val updated_at: String
)