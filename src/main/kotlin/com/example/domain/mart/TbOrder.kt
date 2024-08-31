package com.example.domain.mart

import com.example.domain.OrderStatus
import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@QueryEntity
class TbOrder(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val orderId: Long? = null,
    @Column
    val userId: Long,
    @Column
    val productId: Long,
    @Column
    val quantity: Int,
    @Enumerated(STRING)
    @Column
    var orderStatus: OrderStatus,
) {
    @Column
    val paymentId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    lateinit var tbProduct: TbProduct

    fun getTotalPrice(): Int {
        return quantity * tbProduct.price
    }
}