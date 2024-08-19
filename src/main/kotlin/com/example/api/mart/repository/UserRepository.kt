package com.example.api.mart.repository;

import com.example.domain.mart.TbUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<TbUser, Long> {
    fun findByCountryIdAndUserId(countryId: Long, userId: Long): TbUser?

    @Query("SELECT MAX(u.registrationNumber) FROM TbUser u")
    fun findLastRegistrationNumber(): Int?
}