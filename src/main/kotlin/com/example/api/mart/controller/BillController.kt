package com.example.api.mart.controller

import com.example.api.mart.dto.BillDto
import com.example.api.mart.dto.ReceiptRequestDto
import com.example.api.mart.service.BillService
import com.example.api.util.ResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bills")
class BillController(
    private val billService: BillService
) {
    @PostMapping
    fun requestBill(@RequestBody receiptRequestDto: ReceiptRequestDto): ResponseDto<BillDto> {
        return ResponseDto(billService.requestReceipt(receiptRequestDto))
    }
}