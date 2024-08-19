package com.example.api.mart.dto

data class BillDto(
    val price: Int,
)

data class ReceiptRequestDto(
    val userId: Long
)