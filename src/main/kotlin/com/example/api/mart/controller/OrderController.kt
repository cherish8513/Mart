package com.example.api.mart.controller

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.api.mart.dto.OrderDto
import com.example.api.mart.dto.OrderPostDto
import com.example.api.mart.service.OrderService
import com.example.api.util.PageDto
import com.example.api.util.ResponseDto
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping
    fun addOrder(@Valid @RequestBody orderPostDto: OrderPostDto): ResponseDto<Unit> {
        return ResponseDto(orderService.addOrder(orderPostDto))
    }

    @GetMapping
    fun getBeforePayOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): ResponseDto<PageDto<OrderDto>> {
        return ResponseDto(orderService.getPayBeforeOrders(beforePayOrderPageGetDto))
    }
}