package com.thebookstore.controller.dtos.response

class PageResponseDto<T> (
    val items: List<T>,
    val currentPage: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)
