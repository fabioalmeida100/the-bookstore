package com.mercadolivro.controller

import com.mercadolivro.controller.dtos.request.PostCustomerModelRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerModelRequestDto
import com.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("customers")
class CustomerController() {
        val customers = mutableListOf<CustomerModel>()

        init {
            customers.add(CustomerModel(UUID.randomUUID().toString(), "João", "joao2022@gmail.com"))
        }

        @GetMapping
        fun getAll(@RequestParam name: String?): List<CustomerModel> {
            name?.let {
                return customers.filter { it.name.contains(name, true) }
            }
            return customers
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        fun create(@RequestBody customerModelRequestDto: PostCustomerModelRequestDto) {
            customers.add(CustomerModel(UUID.randomUUID().toString(),customerModelRequestDto.name, customerModelRequestDto.email))
        }

        @GetMapping("/{id}")
        fun getCustomer(@PathVariable id: String): CustomerModel? {
            val customer = customers.find { it.id == id }

            return customer ?: null
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun update(@PathVariable id: String, @RequestBody putCustomerModelRequestDto: PutCustomerModelRequestDto) {
            var customer = customers.first { it.id == id }.let {
                it.name = putCustomerModelRequestDto.name
                it.email = putCustomerModelRequestDto.email
            }
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun delete(@PathVariable id: String) {
            customers.removeIf { it.id == id }
        }
}