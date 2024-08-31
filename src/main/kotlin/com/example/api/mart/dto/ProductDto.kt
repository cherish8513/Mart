package com.example.api.mart.dto

data class ProductDto(
    val productId: Long,
    val name: String,
    val price: Int
)

data class ProductPutDto(
    val productId: Long,
    val quantity: Int,
)