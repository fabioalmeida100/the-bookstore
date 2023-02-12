package com.mercadolivro.controller.dtos.response

class PageResponse<T> (
    val items: List<T>,
    val currentPage: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)


