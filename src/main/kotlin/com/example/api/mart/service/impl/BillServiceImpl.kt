package com.example.api.mart.service.impl

import com.example.api.mart.dto.BillDto
import com.example.api.mart.dto.ReceiptRequestDto
import com.example.api.mart.repository.OrderRepository
import com.example.api.mart.service.BillService
import com.example.api.mart.service.ProductService
import com.example.api.mart.service.TransactionalLockHelper
import com.example.api.mart.service.impl.UserServiceImpl.Companion.REGISTRATION_NUMBER_LOCK_ID
import com.example.api.static.exception.CustomExceptionType
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Transactional(rollbackFor = [Exception::class])
@Service
class BillServiceImpl(
    private val orderRepository: OrderRepository,
    private val productService: ProductService,
    private val transactionalLockHelper: TransactionalLockHelper
): BillService {
    override fun requestReceipt(receiptRequestDto: ReceiptRequestDto): BillDto {
        val paymentTargetOrders = orderRepository.findPayBeforeByUserId(receiptRequestDto.userId)
        var price = 0 
        paymentTargetOrders.forEach {
            if (transactionalLockHelper.lockAndRegisterUnlock(PRODUCT_LOCK_ID+":${it.tbProduct.productId}")) {
                price += productService.decreaseQuantityAndGetTotalPrice(it.productId, it.quantity)
            } else {
                throw CustomExceptionType.RETRY_REQUEST.toException()
            }
        }

        return BillDto(price)
    }
    
    companion object {
        const val PRODUCT_LOCK_ID = "BILL:PRODUCT:LOCK"
    }
}