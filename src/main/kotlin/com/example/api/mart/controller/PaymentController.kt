package com.example.api.mart.controller

import com.example.api.mart.dto.PaymentDto
import com.example.api.mart.dto.PaymentRequestDto
import com.example.api.mart.service.PaymentService
import com.example.api.util.ResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bills")
class PaymentController(
    private val paymentService: PaymentService
) {
    @PostMapping
    fun requestPayment(@RequestBody paymentRequestDto: PaymentRequestDto): ResponseDto<PaymentDto> {
        return ResponseDto(paymentService.requestPayment(paymentRequestDto))
    }
}