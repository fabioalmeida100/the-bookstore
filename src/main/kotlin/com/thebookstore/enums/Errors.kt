package com.thebookstore.enums

enum class Errors (val code: String, val message: String) {
    ML000("ML-000", "Access denied"),
    ML001("ML001", "Invalid request"),
    ML101("ML-101", "Book [%s] not found"),
    ML102("ML-102", "Cannot update book with status [%s]"),
    ML201("ML-201", "Customer [%s] not found"),
}