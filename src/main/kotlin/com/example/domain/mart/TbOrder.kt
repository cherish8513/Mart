package com.example.domain.mart

import com.querydsl.core.annotations.QueryEntity
import com.example.domain.OrderStatusCode
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.EnumType.STRING

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
    var orderStatusCode: OrderStatusCode,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    lateinit var tbProduct: TbProduct

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    lateinit var tbUser: TbUser
}