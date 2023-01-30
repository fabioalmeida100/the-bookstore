package com.mercadolivro.controller

import com.mercadolivro.controller.dtos.request.PostPurchaseRequest
import com.mercadolivro.controller.mapper.PurchaseMapping
import com.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/purchase")
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