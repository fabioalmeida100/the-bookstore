package com.thebookstore.controller

import com.thebookstore.controller.dtos.request.PostBookRequestDto
import com.thebookstore.controller.dtos.request.PutBookRequestDto
import com.thebookstore.controller.dtos.response.BookResponse
import com.thebookstore.controller.dtos.response.PageResponse
import com.thebookstore.extension.toBookModel
import com.thebookstore.extension.toPageResponse
import com.thebookstore.extension.toResponse
import com.thebookstore.service.BookService
import com.thebookstore.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostBookRequestDto) {
        val customer = customerService.getById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

    @GetMapping
    fun getAll(@PageableDefault(page = 0, size = 2) pageable: Pageable): PageResponse<BookResponse> {
        return bookService.getAll(pageable).map { it.toResponse() }.toPageResponse()
    }

    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 2) pageable: Pageable): Page<BookResponse> {
        return bookService.findActives(pageable).map { it.toResponse() }
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
