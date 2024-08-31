package com.example.api.mart.repository

import com.example.domain.mart.TbPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<TbPayment, Long>