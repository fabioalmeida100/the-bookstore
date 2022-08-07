package com.mercadolivro.service

import com.mercadolivro.controller.dtos.request.PostCustomerModelRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerModelRequestDto
import com.mercadolivro.extension.ToCustomerModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.ICustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(val customerRepository: ICustomerRepository) {

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

    fun create(customerModelRequestDto: PostCustomerModelRequestDto) {
        customerRepository.save(CustomerModel(name = customerModelRequestDto.name, email = customerModelRequestDto.email))
    }

    fun getCustomer(id: Int): CustomerModel? {
        return customerRepository.findByIdOrNull(id);
    }

    fun update(id: Int, putCustomerModelRequestDto: PutCustomerModelRequestDto) {
        if (customerRepository.existsById(id)) {
            var customer = putCustomerModelRequestDto.ToCustomerModel()
            customer.id = id
            customerRepository.save(customer)
        }
    }

    fun delete(id: Int) {
        customerRepository.deleteById(id)
    }


}
