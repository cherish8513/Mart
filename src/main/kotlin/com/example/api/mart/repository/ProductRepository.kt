package com.example.api.mart.repository

import com.example.domain.mart.TbProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<TbProduct, Long> {
}