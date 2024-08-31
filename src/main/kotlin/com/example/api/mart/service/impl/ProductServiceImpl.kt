package com.example.api.mart.service.impl

import com.example.api.mart.dto.DecreaseProductResultDto
import com.example.api.mart.dto.ProductDto
import com.example.api.mart.dto.ProductPutDto
import com.example.api.mart.repository.ProductRepository
import com.example.api.mart.service.ProductService
import com.example.api.mart.service.TransactionalLockHelper
import com.example.api.static.exception.CustomException
import com.example.api.static.exception.CustomExceptionType
import com.example.api.util.LoggerUtil
import com.example.api.util.assertNotNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val transactionalLockHelper: TransactionalLockHelper
) : ProductService {
    override fun decreaseStock(productPutDtoList: List<ProductPutDto>): DecreaseProductResultDto {
        val productIds = productPutDtoList.map { it.productId }

        if (!productPutDtoList.all { transactionalLockHelper.lockAndRegisterUnlock("$PRODUCT_LOCK_ID:${it.productId}") }) {
            throw CustomExceptionType.RETRY_REQUEST.toException()
        }

        val quantityMap = productPutDtoList.associateBy({ it.productId }, { it.quantity })
        val foundProducts = productRepository.findAllById(productIds)
        val successDecreasedProducts = mutableListOf<ProductDto>()
        val failDecreasedProducts = mutableListOf<ProductDto>()
        foundProducts.forEach { product ->
            val quantity = quantityMap[product.productId].assertNotNull()
            try {
                product.decrease(quantity)
                successDecreasedProducts.add(
                    ProductDto(
                        productId = product.productId.assertNotNull(),
                        name = product.name,
                        price = product.price * quantity
                    )
                )
            } catch (e: CustomException) {
                logger.debug(e.message)
                failDecreasedProducts.add(
                    ProductDto(
                        productId = product.productId.assertNotNull(),
                        name = product.name,
                        price = product.price * quantity
                    )
                )
            }
        }

        return DecreaseProductResultDto(
            success = successDecreasedProducts,
            fail = failDecreasedProducts,
        )
    }

    companion object {
        val logger = LoggerUtil.getLogger<ProductService>()
        const val PRODUCT_LOCK_ID = "BILL:PRODUCT:LOCK"
    }
}