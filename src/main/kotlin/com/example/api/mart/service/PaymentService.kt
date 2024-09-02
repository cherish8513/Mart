package com.example.api.mart.service

import com.example.api.mart.dto.DeliveryRequestDto
import com.example.api.mart.dto.PaymentDto
import com.example.api.mart.dto.PaymentRequestDto

interface PaymentService {
    fun requestPayment(paymentRequestDto: PaymentRequestDto): PaymentDto
    fun prepareDelivery(deliveryRequestDto: DeliveryRequestDto)
}