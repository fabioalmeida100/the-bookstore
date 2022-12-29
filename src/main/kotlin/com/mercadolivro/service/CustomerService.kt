package com.mercadolivro.service

import com.mercadolivro.controller.dtos.request.PostCustomerModelRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerModelRequestDto
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import com.mercadolivro.repository.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
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
        return customerRepository.findByIdOrNull(id);
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


}
