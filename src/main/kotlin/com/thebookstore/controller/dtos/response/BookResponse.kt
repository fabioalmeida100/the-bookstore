package com.thebookstore.controller.dtos.response

import com.thebookstore.enums.BookStatus
import com.thebookstore.model.Customer
import java.math.BigDecimal

data class BookResponse(
    val id: Int? = null,
    val name: String,
    val price: BigDecimal,
    val status: BookStatus? = null,
    val customer: Customer? = null
)
