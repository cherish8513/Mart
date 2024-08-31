package com.example.api.mart.dto

import com.example.api.util.validator.StringDateTimeFormat
import com.example.domain.Gender
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserDto(
    val userId: Long,
    val name: String,
    val gender: Gender? = null,
    val registrationNumber: Int,
    val birthday: String? = null,
    val phoneNumber: String? = null,
    val point: Long,
)

data class UserPostDto(
    val countryId: Long,
    @field:StringDateTimeFormat(message = "날짜 패턴이 올바르지 않습니다.")
    val receptionDate: String,
    @field:Size(max = 45)
    val name: String,
    val gender: Gender? = null,
    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    val phoneNumber: String? = null,
    val birthYmd: String,
)

data class UserPutDto(
    var userId: Long,
    @field:Size(max = 45)
    var name: String? = null,
    var gender: Gender? = null,
    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber: String? = null,
)

data class UserGetDto(
    val countryId: Long,
    val userId: Long,
)