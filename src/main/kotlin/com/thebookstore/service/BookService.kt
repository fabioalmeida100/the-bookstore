package com.thebookstore.service

import com.thebookstore.enums.BookStatus
import com.thebookstore.enums.Errors
import com.thebookstore.exception.NotFoundException
import com.thebookstore.model.Book
import com.thebookstore.model.Customer
import com.thebookstore.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {
    fun create(book: Book) {
        bookRepository.save(book)
    }

    fun getAll(pageable: Pageable): Page<Book> {
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<Book> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    fun findById(id: Int): Book {
        return bookRepository
            .findById(id)
            .orElseThrow { NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code) }
    }

    fun delete(id: Int) {
        val book = findById(id)
        book.status = BookStatus.CANCELADO

        update(book)
    }

    fun update(book: Book) {
        bookRepository.save(book)
    }

    fun deleteByCustomer(customer: Customer) {
        val books = bookRepository.findByCustomer(customer)
        books.forEach {
            it.status = BookStatus.CANCELADO
        }
        bookRepository.saveAll(books)
    }

    fun geAllById(books: Set<Int>): List<Book> {
        return bookRepository.findAllById(books)
    }
}
