package com.example.domain.mart

import com.querydsl.core.annotations.QueryEntity
import com.example.domain.GenderCode
import org.hibernate.annotations.DynamicUpdate
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.EnumType.STRING

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
    var genderCode: GenderCode? = null,
    @Column(length = 10)
    val birthYmd: String,
    @Column(length = 20)
    var phoneNumber: String? = null,
    @Column
    var point: Long = 0
) {
    fun changeUserInfo(
        name: String?,
        genderCode: GenderCode?,
        phoneNumber: String?
    ) {
        this.name = name ?: this.name
        this.genderCode = genderCode ?: this.genderCode
        this.phoneNumber = phoneNumber ?: this.phoneNumber
    }
}