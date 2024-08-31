package com.example.domain.mart

import com.example.domain.PaymentMethod
import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@QueryEntity
class TbPayment(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val paymentId: Long? = null,
    @Column
    @Enumerated(STRING)
    val paymentMethod: PaymentMethod,
    @Column
    val price: Int,
)