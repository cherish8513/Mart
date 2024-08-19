package com.example.api.mart.dto

import com.example.api.util.PageRequestDto
import jakarta.validation.constraints.Min

data class OrderPostDto(
    val countryId: Long,
    val userId: Long,
    val productId: Long,
    @field:Min(1)
    val quantity: Int,
)

class BeforePayOrderPageGetRequestDto(
    val userId: Long,
    pageNumber: Int?,
    pageSize: Int = 10,
) : PageRequestDto(pageNumber, pageSize)

data class OrderDto(
    val orderId: Long,
    val productDto: ProductDto,
)