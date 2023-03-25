package com.thebookstore.controller

import com.thebookstore.controller.dtos.request.PostPurchaseRequest
import com.thebookstore.controller.mapper.PurchaseMapping
import com.thebookstore.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/purchases")
class PurchaseController (
    private val purchaseService: PurchaseService,
    private val purchaseConverter: PurchaseMapping
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostPurchaseRequest) {
        val purchase = purchaseConverter.toModel(request)
        purchaseService.create(purchase)
    }

}