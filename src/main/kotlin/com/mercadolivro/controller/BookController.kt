package com.mercadolivro.controller

import com.mercadolivro.controller.dtos.request.PostBookRequestDto
import com.mercadolivro.controller.dtos.request.PutBookRequestDto
import com.mercadolivro.controller.dtos.response.BookResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostBookRequestDto) {
        val customer = customerService.getById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

    @GetMapping
    fun getAll(): List<BookResponse> {
        return bookService.getAll().map { it.toResponse() }
    }

    @GetMapping("/active")
    fun findActives(): List<BookResponse> {
        return bookService.findActives().map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): BookResponse {
        return bookService.findById(id).toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody request: PutBookRequestDto) {
        var book = bookService.findById(id)
        bookService.update(request.toBookModel(book))
    }
}