package com.thebookstore.controller.dtos.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequestDto(
        @field: NotEmpty(message = "Nome deve ser preenchido")
        val name: String,

        @field: Email(message = "Email deve ser válido")
        val email: String,
)

