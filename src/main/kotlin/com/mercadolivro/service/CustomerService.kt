package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.enums.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.Customer
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrytpt: BCryptPasswordEncoder
) {

    fun getAll(name: String?): List<Customer> {
        /*var customers = customerRepository.findAll()

        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }*/

        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        return customerRepository.findAll() .toList()
    }

    fun create(customer: Customer) {
        val customerCopy = customer.copy(
            roles = setOf(Role.CUSTOMER),
            password = bCrytpt.encode(customer.password)
        )
        customerRepository.save(customerCopy)
    }

    fun getById(id: Int): Customer? {
        return customerRepository
            .findById(id)
            .orElseThrow{ NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code) }
    }

    fun update(customer: Customer) {
        val customerId = customer.id ?: throw Exception("Customer not found")
        if (customerRepository.existsById(customerId)){
            customerRepository.save(customer)
        }
    }

    fun delete(id: Int) {
        val customer = getById(id)
        if (customer != null) {
            bookService.deleteByCustomer(customer)
            customer.status = CustomerStatus.INATIVO
            customerRepository.save(customer)
        }
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }
}
