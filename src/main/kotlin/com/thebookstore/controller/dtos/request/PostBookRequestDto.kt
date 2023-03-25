package com.thebookstore.controller.dtos.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PostBookRequestDto (
    var name: String,

    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
)


