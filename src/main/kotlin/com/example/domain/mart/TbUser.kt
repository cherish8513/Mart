package com.example.domain.mart

import com.example.domain.Gender
import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.DynamicUpdate

@Entity
@QueryEntity
@DynamicUpdate
class TbUser(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    val userId: Long? = null,
    @Column
    val countryId: Long,
    @Column(length = 45)
    var name: String,
    @Column(unique = true)
    val registrationNumber: Int,
    @Column
    @Enumerated(STRING)
    var gender: Gender? = null,
    @Column(length = 10)
    val birthYmd: String,
    @Column(length = 20)
    var phoneNumber: String? = null,
    @Column
    var point: Long = 0
) {
    fun changeUserInfo(
        name: String?,
        gender: Gender?,
        phoneNumber: String?
    ) {
        this.name = name ?: this.name
        this.gender = gender ?: this.gender
        this.phoneNumber = phoneNumber ?: this.phoneNumber
    }
}