package com.example.api.mart.service.impl

import com.example.api.mart.repository.ProductRepository
import com.example.api.mart.service.ProductService
import com.example.api.util.assertNotNull
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {
    override fun decreaseQuantityAndGetTotalPrice(productId: Long, quantity: Int): Int {
        return productRepository.findByIdOrNull(productId).assertNotNull().let {
            it.decrease(quantity)
            it.price * quantity
        }
    }
}