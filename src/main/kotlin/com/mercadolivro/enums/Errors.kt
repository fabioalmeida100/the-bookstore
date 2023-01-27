package com.mercadolivro.enums

enum class Errors (val code: String, val message: String) {
    ML101("ML-101", "Book [%s] not found"),
    ML201("ML-201", "Customer [%s] not found"),
}