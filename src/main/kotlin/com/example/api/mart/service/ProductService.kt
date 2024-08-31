package com.example.api.mart.service

import com.example.api.mart.dto.DecreaseProductResultDto
import com.example.api.mart.dto.ProductPutDto

interface ProductService {
    fun decreaseStock(productPutDtoList: List<ProductPutDto>): DecreaseProductResultDto
}