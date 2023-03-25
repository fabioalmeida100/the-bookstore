package com.thebookstore.controller

import com.thebookstore.controller.dtos.request.PostCustomerRequestDto
import com.thebookstore.controller.dtos.request.PutCustomerRequestDto
import com.thebookstore.controller.dtos.response.CustomerResponse
import com.thebookstore.extension.toCustomerModel
import com.thebookstore.extension.toResponse
import com.thebookstore.security.UserCanOnlyAccessTheirOwnResource
import com.thebookstore.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
@EnableGlobalMethodSecurity(prePostEnabled = true)
class CustomerController(private val customerService: CustomerService) {
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