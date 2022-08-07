package com.mercadolivro.extension

import com.mercadolivro.controller.dtos.request.PutCustomerModelRequestDto
import com.mercadolivro.model.CustomerModel

fun PutCustomerModelRequestDto.ToCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}