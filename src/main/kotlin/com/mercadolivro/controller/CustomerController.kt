package com.mercadolivro.controller

import com.mercadolivro.controller.dtos.request.PostCustomerRequestDto
import com.mercadolivro.controller.dtos.request.PutCustomerRequestDto
import com.mercadolivro.controller.dtos.response.CustomerResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toResponse
import com.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
        @UserCanOnlyAccessTheirOwnResource
        fun getCustomer(@PathVariable id: Int): CustomerResponse? {
            return customerService.getById(id)?.toResponse()
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @UserCanOnlyAccessTheirOwnResource
        fun update(@PathVariable id: Int, @RequestBody @Valid putCustomerModelRequestDto: PutCustomerRequestDto) {
            val customerModelSaved = customerService.getById(id)
            customerService.update(putCustomerModelRequestDto.toCustomerModel(customerModelSaved!!))
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        fun delete(@PathVariable id: Int) {
            customerService.delete(id)
        }
}