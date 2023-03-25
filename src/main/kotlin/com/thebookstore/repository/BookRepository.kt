package com.thebookstore.repository

import com.thebookstore.enums.BookStatus
import com.thebookstore.model.Book
import com.thebookstore.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Int> {
    fun findByStatus(ativo: BookStatus, pageable: Pageable): Page<Book>
    fun findByCustomer(customer: Customer): List<Book>

    //fun findAll(pageable: Pageable): Page<BookModel> When use CrudRepository
}