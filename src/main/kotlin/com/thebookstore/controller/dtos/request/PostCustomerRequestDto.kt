package com.thebookstore.controller.dtos.request

import com.thebookstore.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequestDto(
        @field: NotEmpty (message = "Nome deve ser preenchido")
        val name: String,

        @field: Email (message = "Email deve ser válido")
        @EmailAvailable
        val email: String,

        @field: NotEmpty (message = "Password deve ser preenchido")
        val password: String
)

