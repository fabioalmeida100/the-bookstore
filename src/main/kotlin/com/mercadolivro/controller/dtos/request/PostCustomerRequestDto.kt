package com.mercadolivro.controller.dtos.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequestDto(
        @field: NotEmpty
        val name: String,

        @field: Email
        val email: String,
)

