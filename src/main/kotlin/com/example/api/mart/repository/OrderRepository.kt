package com.example.api.mart.repository

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.domain.mart.TbOrder
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<TbOrder, Long>, DslOrderRepository

@Repository
interface DslOrderRepository {
    fun findPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): Page<TbOrder>
}