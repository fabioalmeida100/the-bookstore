package com.thebookstore.controller.mapper

import com.thebookstore.controller.dtos.request.PostPurchaseRequest
import com.thebookstore.model.PurchaseModel
import com.thebookstore.service.BookService
import com.thebookstore.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapping(private val customerService: CustomerService,
    private val bookService: BookService) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getById(request.customerId!!)
        val books = bookService.geAllById(request.bookIds)

        return PurchaseModel(
            customer = customer!!,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }

}
