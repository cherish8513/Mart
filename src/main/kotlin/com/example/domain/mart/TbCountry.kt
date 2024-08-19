package com.example.domain.mart

import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@QueryEntity
class TbCountry(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val countryId: Long? = null,
    @Column(length = 100)
    var name: String,
)