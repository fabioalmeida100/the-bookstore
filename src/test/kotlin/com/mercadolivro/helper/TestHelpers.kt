package com.mercadolivro.helper

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import java.util.UUID

fun buildCustomer(
    id: Int? = null,
    name: String = "John Doe",
    email: String = "${UUID.randomUUID()}@gmail.com",
) = CustomerModel(
    id = id,
    name = name,
    email = email,
    status = CustomerStatus.ATIVO)