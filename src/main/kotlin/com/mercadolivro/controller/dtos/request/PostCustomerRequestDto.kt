package com.mercadolivro.controller.dtos.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequestDto(
        @field: NotEmpty (message = "Nome deve ser preenchido")
        val name: String,

        @field: Email (message = "Email deve ser válido")
        val email: String,
)

