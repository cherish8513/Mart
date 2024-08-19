package com.example.api.util

import com.example.api.static.exception.CustomExceptionType
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.validator.constraints.Range
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

open class PageRequestDto(
    val pageNumber: Int?,
    @field:Range(min = 1, max = 100)
    val pageSize: Int = 10
) {
    init {
        if (pageNumber != null && pageNumber < 1) {
            throw CustomExceptionType.INVALID_PARAMETER.toException("pageNumber: $pageNumber")
        }
    }

    fun toPageable(): Pageable {
        return PageRequest.of(pageNumber?.minus(1) ?: 0, pageSize)
    }
}

class PageDto<T>(val list: List<T>, val totalCount: Long = 0) {
    constructor(list: List<T>) : this(list, list.size.toLong())
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseDto<T>(
    @JsonInclude(JsonInclude.Include.ALWAYS)
    val data: T?,
    val resultMessage: String? = null,
    val resultCode: String? = null,
    val ts: Long = System.currentTimeMillis()
) {
    @JsonIgnore
    fun getNonNullData(): T {
        return data ?: throw CustomExceptionType.UNEXPECTED_ERROR_OCCURRED.toException("resultCode: $resultCode, resultMessage: $resultMessage")
    }
}
