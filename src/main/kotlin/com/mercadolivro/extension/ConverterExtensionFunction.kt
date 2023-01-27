package com.mercadolivro.extension

import com.mercadolivro.controller.dtos.request.PostBookRequestDto
import com.mercadolivro.controller.dtos.request.PostCustomerRequestDto
import com.mercadolivro.controller.dtos.request.PutBookRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerRequestDto
import com.mercadolivro.controller.dtos.response.BookResponse
import com.mercadolivro.controller.dtos.response.CustomerResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel

fun PostCustomerRequestDto.toCustomerModel() = CustomerModel(
    name = name,
    email = email,
    status = CustomerStatus.ATIVO
)
fun PutCustomerRequestDto.toCustomerModel(previousCustomer: CustomerModel): CustomerModel {
    return CustomerModel(id = previousCustomer.id,  name = this.name, email = this.email, status = previousCustomer.status)
}

fun PostBookRequestDto.toBookModel(customer: CustomerModel?): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PutBookRequestDto.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        status = previousValue.status!!,
        customer = previousValue.customer
    )
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )
}
