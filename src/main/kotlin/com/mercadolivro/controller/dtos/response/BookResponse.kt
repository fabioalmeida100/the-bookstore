package com.mercadolivro.controller.dtos.response

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.Customer
import java.math.BigDecimal

data class BookResponse (
    val id: Int? = null,
    val name: String,
    val price: BigDecimal,
    val status: BookStatus? = null,
    val customer: Customer? = null
)
