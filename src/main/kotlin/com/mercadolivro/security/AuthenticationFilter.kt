package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.controller.dtos.request.AuthenticationRequestDto
import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationFilter: AuthenticationManager,
    private val customerRepository: CustomerRepository
): UsernamePasswordAuthenticationFilter(authenticationFilter) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val authenticationRequestDto =
                jacksonObjectMapper().readValue(request?.inputStream, AuthenticationRequestDto::class.java)
            val customerId = customerRepository.findByEmail(authenticationRequestDto.email)?.id

            val authToken = UsernamePasswordAuthenticationToken(
                customerId,
                authenticationRequestDto.password
            )
            return authenticationManager.authenticate(authToken)
        } catch (e: Exception) {
            throw AuthenticationException("Falha ao autenticar", "999")
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: javax.servlet.FilterChain?, authResult: Authentication) {
        val id = (authResult.principal as UserCustomDetails).id

        response.addHeader("Authorization", "123456")
    }
}