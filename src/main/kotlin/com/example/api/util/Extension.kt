package com.example.api.util

import com.example.api.static.exception.CustomExceptionType
import com.example.api.static.exception.CustomExceptionType.*
import org.springframework.data.domain.Page

fun <T> T?.assertNotNull(customExceptionType: CustomExceptionType? = null): T {
    return this ?: throw customExceptionType?.toException() ?: UNEXPECTED_ERROR_OCCURRED.toException("expected not null but null")
}

fun <T, U> Page<T>.toPageDto(mapper: (T) -> U): PageDto<U> {
    return PageDto(map(mapper).toList(), totalElements)
}

fun <T> T?.isNotNull(): Boolean {
    return this != null
}