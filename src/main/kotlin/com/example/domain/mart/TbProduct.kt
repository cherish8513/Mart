package com.example.domain.mart

import com.example.api.static.exception.CustomExceptionType
import com.querydsl.core.annotations.QueryEntity
import com.example.domain.Yn
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@QueryEntity
class TbProduct(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val productId: Long? = null,
    @Column(length = 100)
    var name: String,
    @Column
    var price: Int,
    @Column
    var stock: Int,
    @Enumerated(STRING)
    @Column
    var deleteYn: Yn,
) {
    fun decrease(quantity: Int) {
        if(stock - quantity < 0) {
            throw CustomExceptionType.STOCK_NOT_NEGATIVE.toException("$name 품목의 재고가 부족합니다.")
        }
        stock -= quantity
    }
}