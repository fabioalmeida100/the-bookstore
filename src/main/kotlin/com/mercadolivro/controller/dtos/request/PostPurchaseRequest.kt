package com.mercadolivro.controller.dtos.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostPurchaseRequest(
    @field:NotNull
    @field:Positive
    @JsonAlias("customer_id")
    var customerId: Int? = null,

    @field:NotNull
    @JsonAlias("book_ids")
    var bookIds: Set<Int>
)
