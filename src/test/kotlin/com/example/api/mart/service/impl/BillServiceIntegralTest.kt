package com.example.api.mart.service.impl

import com.example.api.mart.dto.ReceiptRequestDto
import com.example.api.mart.service.BillService
import com.example.api.static.exception.CustomException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import kotlin.test.Test

@SpringBootTest
class BillServiceIntegralTest {
    @Autowired
    lateinit var billService: BillService

    @Test
    fun `Cannot drop to negative stock quantity`() {
        // given
        // data.sql 초기 세팅
        val executor = Executors.newFixedThreadPool(3)
        val generateUserIds = listOf(1L, 2L, 3L)

        // when
        val tasks = generateUserIds.map { index ->
            Callable {
                billService.requestReceipt(ReceiptRequestDto(userId = index))
            }
        }

        var successCount = 0
        var failCount = 0

        executor.invokeAll(tasks).forEach {
            try {
                it.get()
                successCount++
            } catch (e: ExecutionException) {
                if(e.cause is CustomException) {
                    println(e.cause)
                    failCount++
                }
            }
        }

        // then
        println(successCount)
        println(failCount)
    }
}