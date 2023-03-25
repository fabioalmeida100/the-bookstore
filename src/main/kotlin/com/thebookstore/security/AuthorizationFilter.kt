package com.thebookstore.security

import com.thebookstore.exception.AuthenticationException
import com.thebookstore.service.UserDetailsCustomerService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userDetails: UserDetailsCustomerService,
    private val jwtUtil: JwtUtil
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val tokenJwt = getAuthentication(authorizationHeader.split(" ")[1])
            SecurityContextHolder.getContext().authentication = tokenJwt
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (!jwtUtil.isValid(token)) {
            throw AuthenticationException("Token inválido", "999")
        }

        val customerId = jwtUtil.getCustomerId(token)
        val user = userDetails.loadUserByUsername(customerId)

        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}
