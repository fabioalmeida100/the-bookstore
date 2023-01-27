package com.mercadolivro.exception

import com.mercadolivro.controller.dtos.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httoCode = 400,
            message = "Este recurso não existe",
            internalCode = "0001",
            errors = null
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}