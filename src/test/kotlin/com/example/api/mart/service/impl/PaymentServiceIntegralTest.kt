package com.example.api.mart.service.impl

import com.example.api.mart.dto.DeliveryRequestDto
import com.example.api.mart.dto.PaymentRequestDto
import com.example.api.mart.service.PaymentService
import com.example.api.static.exception.CustomException
import com.example.domain.PaymentMethod
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import shouldBe
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import kotlin.test.Test

@SpringBootTest
class PaymentServiceIntegralTest {
    @Autowired
    lateinit var paymentService: PaymentService

    @Test
    fun `Cannot drop to negative stock quantity`() {
        // given
        // data.sql 초기 세팅
        val executor = Executors.newFixedThreadPool(3)
        val paymentTestSamples = listOf(
            PaymentTestSample(
                userId = 1,
                orderIds = listOf(1, 2, 3)
            ),
            PaymentTestSample(
                userId = 2,
                orderIds = listOf(4, 5, 6)
            ),
            PaymentTestSample(
                userId = 3,
                orderIds = listOf(7, 8, 9)
            ),
        )

        // when
        val afterPayments = paymentTestSamples.map {
            paymentService.requestPayment(
                PaymentRequestDto(
                    userId = it.userId,
                    orderIds = it.orderIds,
                    paymentMethod = PaymentMethod.CARD
                )
            )
        }

        val tasks = afterPayments.map { toDelivery ->
            Callable {
                paymentService.prepareDelivery(
                    DeliveryRequestDto(
                        userId = toDelivery.userId,
                        paymentId = toDelivery.paymentId
                    )
                )
            }
        }

        var successCount = 0
        var failCount = 0

        executor.invokeAll(tasks).forEach {
            try {
                it.get()
                successCount++
            } catch (e: ExecutionException) {
                if (e.cause is CustomException) {
                    println(e.cause)
                    failCount++
                }
            }
        }

        // then
        successCount shouldBe paymentTestSamples.size
    }

    data class PaymentTestSample(
        val userId: Long,
        val orderIds: List<Long>
    )
}