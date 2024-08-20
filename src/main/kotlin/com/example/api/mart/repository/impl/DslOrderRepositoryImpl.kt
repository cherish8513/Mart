package com.example.api.mart.repository.impl

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.api.mart.repository.DslOrderRepository
import com.example.api.util.fetchPage
import com.example.domain.OrderStatusCode
import com.example.domain.mart.QTbOrder.tbOrder
import com.example.domain.mart.TbOrder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page

class DslOrderRepositoryImpl(private val query: JPAQueryFactory) : DslOrderRepository {
    override fun findPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): Page<TbOrder> {
        return query
            .select(tbOrder)
            .from(tbOrder)
            .innerJoin(tbOrder.tbProduct).fetchJoin()
            .where(
                tbOrder.userId.eq(beforePayOrderPageGetDto.userId),
                tbOrder.orderStatusCode.eq(OrderStatusCode.PAY_BEFORE)
            )
            .fetchPage(beforePayOrderPageGetDto.toPageable())
    }
}