package com.example.api.mart.service

interface ProductService {
    fun decreaseQuantityAndGetTotalPrice(productId: Long, quantity: Int): Int
}