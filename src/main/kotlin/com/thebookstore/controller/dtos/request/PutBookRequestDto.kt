package com.thebookstore.controller.dtos.request

import java.math.BigDecimal

data class PutBookRequestDto (
    var name: String?,
    var price: BigDecimal?
)
