package com.example.api.mart.dto

import com.example.domain.PaymentMethod

data class PaymentRequestDto(
    val userId: Long,
    val orderIds: List<Long>,
    val paymentMethod: PaymentMethod,
)

data class PaymentDto(
    val userId: Long,
    val paymentId: Long,
)

data class DeliveryRequestDto(
    val userId: Long,
    val paymentId: Long
)