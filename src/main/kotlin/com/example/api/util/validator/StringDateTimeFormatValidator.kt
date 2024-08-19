package com.example.api.util.validator

import com.example.api.util.assertNotNull
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StringDateTimeFormatValidator : ConstraintValidator<StringDateTimeFormat, String> {
    private lateinit var annotation: StringDateTimeFormat

    override fun initialize(constraintAnnotation: StringDateTimeFormat) {
        this.annotation = constraintAnnotation
    }

    override fun isValid(dateFormatString: String?, context: ConstraintValidatorContext): Boolean {
        val pattern = annotation.pattern

        return try {
            LocalDateTime.parse(dateFormatString.assertNotNull(), DateTimeFormatter.ofPattern(pattern))
            true
        } catch (e: Exception) {
            false
        }
    }

}