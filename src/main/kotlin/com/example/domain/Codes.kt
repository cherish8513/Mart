package com.example.domain

interface ICode {
    val description: String
    val code: Char
}

enum class Gender(
    override val description: String,
    override val code: Char
) : ICode {
    MALE("남", 'M'),
    FEMALE("여", 'F');
}

enum class OrderStatus(
    override val description: String,
    override val code: Char
) : ICode {
    PAY_BEFORE("결제전", '1'),
    PAY_AFTER("결제후", '2'),
    PAY_CANCEL("결제취소", '3'),
}

enum class Yn {
    Y, N
}

enum class PaymentMethod(
    override val description: String,
    override val code: Char
) : ICode {
    CASH("현금", '1'),
    CARD("카드", '2')
}