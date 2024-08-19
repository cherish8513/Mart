package com.example.api.mart.service

import com.example.api.mart.dto.BillDto
import com.example.api.mart.dto.ReceiptRequestDto

interface BillService {
    fun requestReceipt(receiptRequestDto: ReceiptRequestDto): BillDto
}