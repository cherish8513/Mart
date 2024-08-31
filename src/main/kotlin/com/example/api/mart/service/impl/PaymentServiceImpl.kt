package com.example.api.mart.service.impl

import com.example.api.mart.dto.PaymentDto
import com.example.api.mart.dto.PaymentRequestDto
import com.example.api.mart.repository.OrderRepository
import com.example.api.mart.repository.PaymentRepository
import com.example.api.mart.service.PaymentService
import com.example.api.mart.service.ProductService
import com.example.api.mart.service.TransactionalLockHelper
import com.example.api.static.exception.CustomExceptionType
import com.example.api.util.assertNotNull
import com.example.domain.mart.TbPayment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(rollbackFor = [Exception::class])
@Service
class PaymentServiceImpl(
    private val orderRepository: OrderRepository,
    private val paymentRepository: PaymentRepository,
    private val productService: ProductService,
    private val transactionalLockHelper: TransactionalLockHelper
) : PaymentService {
    override fun requestPayment(paymentRequestDto: PaymentRequestDto): PaymentDto {
        val payBeforeOrders = orderRepository.findByUserIdAndOrderIdIn(
            userId = paymentRequestDto.userId,
            orderIds = paymentRequestDto.orderIds
        )
        if (payBeforeOrders.all { transactionalLockHelper.lockAndRegisterUnlock(PRODUCT_LOCK_ID + ":${it.tbProduct.productId}") }) {
            val price = payBeforeOrders.sumOf { productService.decreaseQuantityAndGetTotalPrice(it.productId, it.quantity) }

            val newPaymentId = paymentRepository.save(
                TbPayment(
                    paymentMethod = paymentRequestDto.paymentMethod,
                    price = price
                )
            ).paymentId.assertNotNull()

            orderRepository.modifyToPayAfter(
                userId = paymentRequestDto.userId,
                orderIds = payBeforeOrders.map { it.orderId.assertNotNull() },
                paymentId = newPaymentId
            )

            return PaymentDto(
                paymentId = newPaymentId,
                paymentMethod = paymentRequestDto.paymentMethod,
                price = price
            )
        } else {
            throw CustomExceptionType.RETRY_REQUEST.toException()
        }
    }

    companion object {
        const val PRODUCT_LOCK_ID = "BILL:PRODUCT:LOCK"
    }
}