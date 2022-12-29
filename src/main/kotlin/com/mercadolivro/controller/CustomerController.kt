package com.mercadolivro.controller

import com.mercadolivro.controller.dtos.request.PostCustomerModelRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerModelRequestDto
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController(val customerService: CustomerService) {

        @GetMapping
        fun getAll(@RequestParam name: String?): List<CustomerModel> {
            return customerService.getAll(name)
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        fun create(@RequestBody customerModelRequestDto: PostCustomerModelRequestDto) {
            val customerModel = customerModelRequestDto.toCustomerModel();
            customerService.create(customerModel)
        }

        @GetMapping("/{id}")
        fun getCustomer(@PathVariable id: Int): CustomerModel? {
            return customerService.getById(id)
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun update(@PathVariable id: Int, @RequestBody putCustomerModelRequestDto: PutCustomerModelRequestDto) {
            val customerModelSaved = customerService.getById(id)
            customerService.update(putCustomerModelRequestDto.toCustomerModel(customerModelSaved!!))
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun delete(@PathVariable id: Int) {
            customerService.delete(id)
        }
}