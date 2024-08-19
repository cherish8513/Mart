package com.example.api.mart.repository;

import com.example.domain.mart.TbCountry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<TbCountry, Long>