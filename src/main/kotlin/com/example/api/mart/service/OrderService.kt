package com.example.api.mart.service

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.api.mart.dto.OrderDto
import com.example.api.mart.dto.OrderPostDto
import com.example.api.util.PageDto
import org.springframework.data.domain.Page

interface OrderService {
    fun addOrder(orderPostDto: OrderPostDto)
    fun getPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): PageDto<OrderDto>
}