package com.example.api.mart.repository

import com.example.api.mart.dto.BeforePayOrderPageGetRequestDto
import com.example.domain.OrderStatus
import com.example.domain.mart.TbOrder
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<TbOrder, Long>, DslOrderRepository {
    @Modifying
    @Query(
        """
        UPDATE TbOrder o
        SET o.orderStatus = :orderStatus, o.paymentId = :paymentId
        WHERE o.userId = :userId
        AND o.orderId IN :orderIds
    """
    )
    fun modifyOrderStatusAndPaymentId(userId: Long, orderIds: List<Long>, orderStatus: OrderStatus, paymentId: Long)

    @Query(
        """
        SELECT o
          FROM TbOrder o
          JOIN FETCH o.tbProduct
         WHERE o.userId = :userId
           AND o.paymentId = :paymentId
    """
    )
    fun findByUserIdAndPaymentId(userId: Long, paymentId: Long): List<TbOrder>
}

@Repository
interface DslOrderRepository {
    fun findPayBeforeOrders(beforePayOrderPageGetDto: BeforePayOrderPageGetRequestDto): Page<TbOrder>
}