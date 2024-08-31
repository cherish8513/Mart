package com.example.api.mart.dto

import com.example.domain.PaymentMethod

data class PaymentDto(
    val paymentId: Long,
    val paymentMethod: PaymentMethod,
    val price: Int,
)

data class PaymentRequestDto(
    val userId: Long,
    val orderIds: List<Long>,
    val paymentMethod: PaymentMethod,
)