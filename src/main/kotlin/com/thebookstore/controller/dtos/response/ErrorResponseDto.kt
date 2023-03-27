package com.thebookstore.controller.dtos.response

data class ErrorResponseDto(
    var httoCode: Int,
    var message: String,
    var internalCode: String,
    var errors: List<FieldErrorResponseDto>?
)
