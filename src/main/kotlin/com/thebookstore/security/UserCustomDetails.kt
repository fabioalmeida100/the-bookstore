package com.thebookstore.security

import com.thebookstore.enums.CustomerStatus
import com.thebookstore.model.Customer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val customer: Customer) : UserDetails {
    val id: Int
        get() = customer.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return customer.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()
    }

    override fun getPassword(): String {
        return customer.password
    }

    override fun getUsername(): String {
        return customer.id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return customer.status == CustomerStatus.ATIVO
    }
}
