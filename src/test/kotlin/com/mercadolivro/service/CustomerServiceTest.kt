package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @Test
    fun `should return all customers`() {
        // Arrange
        val customerLists = listOf(buildCustomer(), buildCustomer())
        every { customerRepository.findAll() } returns customerLists

        // Act
        val customers = customerService.getAll(null)

        // Assert
        assertEquals(customerLists, customers)
        verify(exactly = 1) { customerRepository.findAll() }
        verify(exactly = 0) { customerRepository.findByNameContaining(any()) }
    }

    @Test
    fun `should return specific customer`() {
        // Arrange
        val name = "Fabin-${Math.random()}"
        val customers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findByNameContaining(name) } returns customers

        // Act
        val customer = customerService.getAll(name)

        // Assert
        assertEquals(customer, customer)
        verify(exactly = 0) { customerRepository.findAll() }
        verify(exactly = 1) { customerRepository.findByNameContaining(name) }
    }

    fun buildCustomer(
            id: Int? = null,
            name: String = "John Doe",
            email: String = "${UUID.randomUUID()}@gmail.com",
        ) = CustomerModel(
            id = id,
            name = name,
            email = email,
            status = CustomerStatus.ATIVO)
}