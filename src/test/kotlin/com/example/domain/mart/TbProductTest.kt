package com.example.domain.mart

import org.junit.jupiter.api.Test
import shouldBe

class TbProductTest {

    @Test
    fun decrease() {
        // given
        val initialStock = 3
        val product = TbProduct(
            name = "제품",
            price = 1000,
            stock = initialStock
        )

        // when
        val decreaseQuantity = 2
        product.decrease(decreaseQuantity)

        // then
        product.stock shouldBe initialStock - decreaseQuantity
    }
}