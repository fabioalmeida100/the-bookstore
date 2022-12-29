package com.mercadolivro.extension

import com.mercadolivro.controller.dtos.request.PostBookRequestDto
import com.mercadolivro.controller.dtos.request.PostCustomerModelRequestDto
import com.mercadolivro.controller.dtos.request.PutBookRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerModelRequestDto
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel

fun PostCustomerModelRequestDto.toCustomerModel() = CustomerModel(
    name = name,
    email = email,
    status = CustomerStatus.ATIVO
)
fun PutCustomerModelRequestDto.toCustomerModel(previousCustomer: CustomerModel): CustomerModel {
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
        status = previousValue.status,
        customer = previousValue.customer
    )
}
