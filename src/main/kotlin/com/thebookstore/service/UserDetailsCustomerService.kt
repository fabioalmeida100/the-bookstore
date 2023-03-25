package com.thebookstore.service

import com.thebookstore.exception.AuthenticationException
import com.thebookstore.repository.CustomerRepository
import com.thebookstore.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomerService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {
    override fun loadUserByUsername(customerId: String): UserDetails {
        val customer = customerRepository.findById(customerId.toInt())
            .orElseThrow { throw AuthenticationException("Usuário não encontrado", "999") }

        return UserCustomDetails(customer)
    }
}
