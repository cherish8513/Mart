package com.example.api.mart.service.impl

import com.example.api.mart.dto.PaymentRequestDto
import com.example.api.mart.dto.ProductPutDto
import com.example.api.mart.repository.OrderRepository
import com.example.api.mart.repository.PaymentRepository
import com.example.api.mart.service.PaymentService
import com.example.api.mart.service.ProductService
import com.example.api.util.LoggerUtil
import com.example.api.util.assertNotNull
import com.example.domain.OrderStatus
import com.example.domain.mart.TbPayment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(rollbackFor = [Exception::class])
@Service
class PaymentServiceImpl(
    private val orderRepository: OrderRepository,
    private val paymentRepository: PaymentRepository,
    private val paymentHelper: PaymentHelper
) : PaymentService {
    override fun requestPayment(paymentRequestDto: PaymentRequestDto) {
        val payment = paymentRepository.save(
            TbPayment(
                paymentMethod = paymentRequestDto.paymentMethod,
            )
        )

        orderRepository.modifyOrderStatusAndPaymentId(
            userId = paymentRequestDto.userId,
            orderIds = paymentRequestDto.orderIds,
            orderStatus = OrderStatus.PAY_AFTER,
            paymentId = payment.paymentId.assertNotNull()
        )

        paymentHelper.prepareDelivery(userId = paymentRequestDto.userId, paymentId = payment.paymentId.assertNotNull())
    }
}


/**
 * subscribe 가정
 */
@Transactional(rollbackFor = [Exception::class])
@Service
class PaymentHelper(
    private val orderRepository: OrderRepository,
    private val productService: ProductService,
) {
    fun prepareDelivery(userId: Long, paymentId: Long) {
        val payAfterOrders = orderRepository.findByUserIdAndPaymentId(userId = userId, paymentId = paymentId)
        val result = productService.decreaseStock(payAfterOrders.map {
            ProductPutDto(
                productId = it.productId,
                quantity = it.quantity
            )
        })

        if (result.fail.isNotEmpty()) {
            logger.info("Refund : ${result.fail.sumOf { it.price }}")

            orderRepository.modifyOrderStatusAndPaymentId(
                userId = userId,
                orderIds = payAfterOrders.filter { result.fail.map { it.productId }.contains(it.productId) }
                    .map { it.orderId.assertNotNull() },
                orderStatus = OrderStatus.PAY_CANCEL,
                paymentId = paymentId
            )
        }
    }

    companion object {
        val logger = LoggerUtil.getLogger<PaymentHelper>()
    }
}

