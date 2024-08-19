package com.example.api.static.exception

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class CustomException(val exceptionType: CustomExceptionType, message: String?) : RuntimeException(message)

enum class CustomExceptionType(
    val httpStatus: HttpStatus,
    val defaultMessage: String? = null
) {
    UNEXPECTED_ERROR_OCCURRED(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다."),
    INVALID_PARAMETER(HttpStatus.FORBIDDEN, "허용되지 않는 요청 값입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    RETRY_REQUEST(HttpStatus.FORBIDDEN, "요청이 지연되고 있습니다. 잠시 후 다시 시도해주세요."),
    STOCK_NOT_NEGATIVE(HttpStatus.FORBIDDEN);

    fun toException(message: String? = null): CustomException {
        return CustomException(this, message ?: defaultMessage)
    }
}

data class ErrorResponse(
    @field:JsonProperty("result_code")
    var resultCode: String? = null,
    @field:JsonProperty("http_status")
    var httpStatus: String? = null,
    var message: String? = null,
    var path: String? = null,
    var timestamp: LocalDateTime? = null,
    var errors: MutableList<Error>? = mutableListOf()
)

data class Error(
    var field: String? = null,
    var message: String? = null,
    var value: Any? = null
)
