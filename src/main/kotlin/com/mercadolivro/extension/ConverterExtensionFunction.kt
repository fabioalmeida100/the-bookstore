package com.mercadolivro.extension

import com.mercadolivro.controller.dtos.request.PostBookRequestDto
import com.mercadolivro.controller.dtos.request.PostCustomerRequestDto
import com.mercadolivro.controller.dtos.request.PutBookRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerRequestDto
import com.mercadolivro.controller.dtos.response.BookResponse
import com.mercadolivro.controller.dtos.response.CustomerResponse
import com.mercadolivro.controller.dtos.response.PageResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.Book
import com.mercadolivro.model.Customer
import org.springframework.data.domain.Page

fun PostCustomerRequestDto.toCustomerModel() = Customer(
    name = name,
    email = email,
    status = CustomerStatus.ATIVO,
    password = password
)
fun PutCustomerRequestDto.toCustomerModel(previousCustomer: Customer): Customer {
    return Customer(
        id = previousCustomer.id,
        name = name,
        email = email,
        status = previousCustomer.status,
        password = previousCustomer.password)
}

fun PostBookRequestDto.toBookModel(customer: Customer?): Book {
    return Book(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PutBookRequestDto.toBookModel(previousValue: Book): Book {
    return Book(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        status = previousValue.status!!,
        customer = previousValue.customer
    )
}

fun Customer.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun Book.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )
}

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        items = this.content,
        currentPage = this.number,
        size = this.pageable.pageSize,
        totalElements = this.totalElements,
        totalPages = this.totalPages
    )
}
