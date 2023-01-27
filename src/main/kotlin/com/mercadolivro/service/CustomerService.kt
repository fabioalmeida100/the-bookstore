package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository,
    val bookService: BookService) {

    fun getAll(name: String?): List<CustomerModel> {
        /*var customers = customerRepository.findAll()

        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }*/

        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        return customerRepository.findAll() .toList()
    }

    fun create(customerModel: CustomerModel) {
        customerRepository.save(customerModel)
    }

    fun getById(id: Int): CustomerModel? {
        return customerRepository
            .findById(id)
            .orElseThrow{ NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code) }
    }

    fun update(customer: CustomerModel) {
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
