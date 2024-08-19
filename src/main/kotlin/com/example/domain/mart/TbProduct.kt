package com.example.domain.mart

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
)