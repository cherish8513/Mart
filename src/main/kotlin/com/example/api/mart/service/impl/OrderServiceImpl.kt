package com.example.api.mart.service.impl

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.api.mart.dto.OrderDto
import com.example.api.mart.dto.OrderPostDto
import com.example.api.mart.dto.ProductDto
import com.example.api.mart.repository.OrderRepository
import com.example.api.mart.repository.ProductRepository
import com.example.api.mart.repository.UserRepository
import com.example.api.mart.service.OrderService
import com.example.api.util.PageDto
import com.example.api.util.assertNotNull
import com.example.api.util.toPageDto
import com.example.domain.OrderStatusCode
import com.example.domain.mart.TbOrder
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Transactional(rollbackFor = [Exception::class])
@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) : OrderService {
    override fun addOrder(orderPostDto: OrderPostDto) {
        productRepository.findById(orderPostDto.productId).assertNotNull()
        userRepository.findByCountryIdAndUserId(orderPostDto.countryId, orderPostDto.userId).assertNotNull()
        orderRepository.save(
            TbOrder(
                userId = orderPostDto.userId,
                productId = orderPostDto.productId,
                quantity = orderPostDto.quantity,
                orderStatusCode = OrderStatusCode.PAY_BEFORE
            )
        )
    }

    override fun getPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): PageDto<OrderDto> {
        return orderRepository.findPayBeforeOrders(beforePayOrderPageGetDto).toPageDto {
            OrderDto(
                orderId = it.orderId.assertNotNull(),
                productDto = ProductDto(
                    productId = it.tbProduct.productId.assertNotNull(),
                    name = it.tbProduct.name,
                    price = it.tbProduct.price
                )
            )
        }
    }
}