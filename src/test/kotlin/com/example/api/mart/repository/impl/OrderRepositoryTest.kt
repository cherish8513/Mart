package com.example.api.mart.repository.impl

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.api.mart.repository.OrderRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun findPayBeforeOrders() {
        // given
        // data.sql 기본세팅 사용
        val userId = 1L

        // when
        var hasNext = true
        var pageNumber = 1
        while (hasNext) {
            val orders = orderRepository.findPayBeforeOrders(
                BeforePayOrderPageGetRequestDto(
                    userId = userId,
                    pageNumber = pageNumber,
                    pageSize = 1
                )
            )

            hasNext = orders.hasNext()
            pageNumber++

            // then
            orders.map {
                println("orderId : ${it.orderId} quantity: ${it.quantity} status: ${it.orderStatusCode}")
            }
        }
    }
}