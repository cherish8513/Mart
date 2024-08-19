package com.example.domain

interface ICode {
    val description: String
    val code: Char
}

enum class GenderCode(
    override val description: String,
    override val code: Char
): ICode {
    MALE("남", 'M'),
    FEMALE("여", 'F');
}

enum class OrderStatusCode(
    override val description: String,
    override val code: Char
): ICode {
    PAY_BEFORE("결제전", '1'),
    PAY_AFTER("결제후", '2');
}

enum class Yn {
    Y, N
}

enum class PaymentMethodCode {
    CASH, CARD
}