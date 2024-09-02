package com.example.api.mart.service.impl

import com.example.api.mart.dto.DeliveryRequestDto
import com.example.api.mart.dto.PaymentDto
import com.example.api.mart.dto.PaymentRequestDto
import com.example.api.mart.repository.OrderRepository
import com.example.api.mart.repository.PaymentRepository
import com.example.api.mart.repository.ProductRepository
import com.example.api.mart.service.PaymentService
import com.example.api.mart.service.TransactionalLockHelper
import com.example.api.static.exception.CustomException
import com.example.api.static.exception.CustomExceptionType
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
    private val productRepository: ProductRepository,
    private val transactionalLockHelper: TransactionalLockHelper
) : PaymentService {
    override fun requestPayment(paymentRequestDto: PaymentRequestDto): PaymentDto {
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

        return PaymentDto(userId = paymentRequestDto.userId, paymentId = payment.paymentId.assertNotNull())
    }

    /**
     * 결제가 완료 된 물건의 재고를 감소시키고 배송 중 상태로 변경
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun prepareDelivery(deliveryRequestDto: DeliveryRequestDto) {
        val payAfterOrders = orderRepository.findByUserIdAndPaymentId(
            userId = deliveryRequestDto.userId,
            paymentId = deliveryRequestDto.paymentId
        )
        if (!payAfterOrders.all { transactionalLockHelper.lockAndRegisterUnlock("$PRODUCT_LOCK_ID:${it.productId}") }) {
            throw CustomExceptionType.RETRY_REQUEST.toException()
        }

        val quantityMap = payAfterOrders.associateBy({ it.productId }, { it.quantity })
        val foundProducts = productRepository.findAllById(payAfterOrders.map { it.productId })
        val successDecreasedProductIds = mutableListOf<Long>()
        val failDecreasedProductIds = mutableListOf<Long>()
        foundProducts.forEach { product ->
            val productId = product.productId.assertNotNull()
            val quantity = quantityMap[productId].assertNotNull()
            try {
                product.decrease(quantity)
                successDecreasedProductIds.add(productId)
            } catch (e: CustomException) {
                logger.debug(e.message)
                failDecreasedProductIds.add(productId)
            }
        }

        orderRepository.modifyOrderStatusAndPaymentId(
            userId = deliveryRequestDto.userId,
            orderIds = payAfterOrders
                .filter { failDecreasedProductIds.contains(it.productId) }
                .map { it.orderId.assertNotNull() },
            orderStatus = OrderStatus.PREPARE_DELIVERY,
            paymentId = deliveryRequestDto.paymentId
        )
        if (failDecreasedProductIds.isNotEmpty()) {
            // 환불 로직
            failDecreasedProductIds.forEach {
                logger.info("Refund product id: $it, userId: ${deliveryRequestDto.userId}, quantity: ${quantityMap[it]}")
            }

            orderRepository.modifyOrderStatusAndPaymentId(
                userId = deliveryRequestDto.userId,
                orderIds = payAfterOrders
                    .filter { failDecreasedProductIds.contains(it.productId) }
                    .map { it.orderId.assertNotNull() },
                orderStatus = OrderStatus.PAY_CANCEL,
                paymentId = deliveryRequestDto.paymentId
            )
        }
    }

    companion object {
        val logger = LoggerUtil.getLogger<PaymentService>()
        const val PRODUCT_LOCK_ID = "BILL:PRODUCT:LOCK"
    }
}

