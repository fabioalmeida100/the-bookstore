package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional
import java.util.UUID
import kotlin.random.Random

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

    @Test
    fun `should create customer`() {
        // Arrange
        val customer = buildCustomer()
        every { customerRepository.save(customer) } returns customer

        // Act
        val customerCreated = customerService.create(customer)

        // Assert
        assertEquals(customer, customerCreated)
        verify(exactly = 1) { customerRepository.save(customer) }
    }

    @Test
    fun `should return customer by id`() {
        // Arrange
        val customerId = Random.nextInt()
        val fakeCustomer = buildCustomer(id = customerId)
        every { customerRepository.findById(customerId) } returns Optional.of(fakeCustomer)

        // Act
        val customer = customerService.getById(customerId)

        // Assert
        assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customerRepository.findById(customerId) }
    }

    @Test
    fun `should throw error when customer not found`() {
        // Arrange
        val customerId = Random.nextInt()
        every { customerRepository.findById(customerId) } returns Optional.empty()

        // Act
        val error = assertThrows<NotFoundException>{
            customerService.getById(customerId)
        }

        // Assert
        assertEquals("Customer [${customerId}] not found", error.message)
        assertEquals("ML-201", error.errorCode)
        verify(exactly = 1) { customerRepository.findById(customerId) }
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