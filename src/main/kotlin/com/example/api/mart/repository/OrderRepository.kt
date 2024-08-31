package com.example.api.mart.repository

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.domain.mart.TbOrder
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<TbOrder, Long>, DslOrderRepository {
    fun findByUserIdAndOrderIdIn(userId: Long, orderIds: List<Long>): List<TbOrder>

    @Modifying
    @Query(
        """
        UPDATE TbOrder o
        SET o.orderStatus = 'PAY_AFTER', o.paymentId = :paymentId
        WHERE o.userId = :userId
        AND o.orderId IN :orderIds
    """
    )
    fun modifyToPayAfter(userId: Long, orderIds: List<Long>, paymentId: Long)
}

@Repository
interface DslOrderRepository {
    fun findPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): Page<TbOrder>
}