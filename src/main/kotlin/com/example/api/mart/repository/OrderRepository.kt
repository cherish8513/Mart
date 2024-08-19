package com.example.api.mart.repository

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.domain.mart.TbOrder
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<TbOrder, Long>, DslOrderRepository {
    @Query("""
        from TbOrder o
        join fetch o.tbUser
        join fetch o.tbProduct
       where o.userId = :userId 
         and o.orderStatusCode = 'PAY_BEFORE'
    """)
    fun findPayBeforeByUserId(userId: Long): List<TbOrder>
}

@Repository
interface DslOrderRepository {
    fun findPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): Page<TbOrder>
}