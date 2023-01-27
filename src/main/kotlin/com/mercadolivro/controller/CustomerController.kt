package com.mercadolivro.controller

import com.mercadolivro.controller.dtos.request.PostCustomerRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerRequestDto
import com.mercadolivro.controller.dtos.response.CustomerResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(val customerService: CustomerService) {

        @GetMapping
        fun getAll(@RequestParam name: String?): List<CustomerResponse> {
            return customerService.getAll(name).map { it.toResponse() }
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        fun create(@RequestBody @Valid customerModelRequestDto: PostCustomerRequestDto) {
            val customerModel = customerModelRequestDto.toCustomerModel();
            customerService.create(customerModel)
        }

        @GetMapping("/{id}")
        fun getCustomer(@PathVariable id: Int): CustomerResponse? {
            return customerService.getById(id)?.toResponse()
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun update(@PathVariable id: Int, @RequestBody putCustomerModelRequestDto: PutCustomerRequestDto) {
            val customerModelSaved = customerService.getById(id)
            customerService.update(putCustomerModelRequestDto.toCustomerModel(customerModelSaved!!))
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun delete(@PathVariable id: Int) {
            customerService.delete(id)
        }
}