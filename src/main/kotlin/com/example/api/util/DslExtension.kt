package com.example.api.util

import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

fun <T> JPQLQuery<T>.fetchPage(pageable: Pageable, getTotalCount: (() -> Long)? = null): Page<T> {
    limit(pageable.pageSize.toLong()).offset(pageable.offset)
    return PageImpl(fetch(), pageable, getTotalCount?.let { it() } ?: fetchCount())
}