package com.thebookstore.controller.dtos.response

import com.thebookstore.enums.CustomerStatus

data class CustomerResponse(
    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus
)
