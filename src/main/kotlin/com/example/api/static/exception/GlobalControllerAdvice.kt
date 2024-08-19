package com.example.api.static.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalControllerAdvice {
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        constraintViolationException: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        constraintViolationException.constraintViolations.forEach {
            val error = Error().apply {
                this.field = it.propertyPath.last().name
                this.message = it.message
                this.value = it.invalidValue
            }
            errors.add(error)
        }

        return errorResponseEntityGenerator(BAD_REQUEST, request, errors)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(
        methodArgumentNotValidException: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val errors = mutableListOf<Error>()

        methodArgumentNotValidException.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }
            errors.add(error)
        }

        return errorResponseEntityGenerator(BAD_REQUEST, request, errors)
    }

    @ExceptionHandler(value = [CustomException::class])
    fun customException(
        customException: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        return errorResponseEntityGenerator(
            httpStatus = customException.exceptionType.httpStatus,
            message = customException.exceptionType.defaultMessage,
            request = request
        )
    }

    private fun errorResponseEntityGenerator(
        httpStatus: HttpStatus,
        request: HttpServletRequest,
        errors: MutableList<Error>? = mutableListOf(),
        message: String? = "요청에 에러가 발생했습니다."
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = httpStatus.value().toString()
            this.message = message
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.status(httpStatus).body(errorResponse)
    }
}