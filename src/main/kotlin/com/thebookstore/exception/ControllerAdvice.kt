package com.thebookstore.exception

import com.thebookstore.controller.dtos.response.ErrorResponse
import com.thebookstore.controller.dtos.response.FieldErrorResponse
import com.thebookstore.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httoCode = HttpStatus.NOT_FOUND.value(),
            message = e.message,
            internalCode = e.errorCode,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httoCode = HttpStatus.BAD_REQUEST.value(),
            message = e.message,
            internalCode = e.errorCode,
            errors = null
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httoCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            message = Errors.ML001.message,
            internalCode = Errors.ML001.code,
            errors = e.bindingResult.fieldErrors.map {
                FieldErrorResponse(it.defaultMessage ?: "Invalid", it.field)
            }
        )

        return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httoCode = HttpStatus.FORBIDDEN.value(),
            message = Errors.ML000.message,
            internalCode = Errors.ML000.code,
            null
        )

        return ResponseEntity(error, HttpStatus.FORBIDDEN)
    }
}