package com.thebookstore.repository

import com.thebookstore.model.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Int> {
    fun findByNameContaining(name: String): List<Customer>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Customer?
}
