package com.mercadolivro.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        customerRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return a list of customer`() {
        // Arrange
        val customer1 = buildCustomer();
        customerRepository.save(customer1)

        val customer2 = buildCustomer();
        customerRepository.save(customer2)

        // Assert
        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$.[0].id").value(customer1.id))
            .andExpect(jsonPath("$.[0].name").value(customer1.name))
            .andExpect(jsonPath("$.[0].email").value(customer1.email))
            .andExpect(jsonPath("$.[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$.[1].id").value(customer2.id))
            .andExpect(jsonPath("$.[1].name").value(customer2.name))
            .andExpect(jsonPath("$.[1].email").value(customer2.email))
            .andExpect(jsonPath("$.[1].status").value(customer2.status.name))
            .andReturn()
    }

    @Test
    fun `should create a customer`() {
        // Arrange
        val customer = buildCustomer()

        // Act
        mockMvc.perform(post("/customers")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isCreated)
            .andReturn()

        // Assert
        val customerSaved = customerRepository.findAll().first()
        assertEquals(customer.name, customerSaved.name)
        assertEquals(customer.email, customerSaved.email)
        assertEquals(customer.status, customerSaved.status)
    }

    @Test
    fun getCustomer() {
        // Arrange
        val customer = buildCustomer()
        val customerSaved = customerRepository.save(customer)

        // Assert
        mockMvc.perform(get("/customers/${customerSaved.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customerSaved.id))
            .andExpect(jsonPath("$.name").value(customerSaved.name))
            .andExpect(jsonPath("$.email").value(customerSaved.email))
            .andExpect(jsonPath("$.status").value(customerSaved.status.name))
            .andReturn()
    }

    @Test
    fun `should update a customer`() {
        // Arrange
        val customer = buildCustomer()
        val customerSaved = customerRepository.save(customer)

        // Act
        customerSaved.name = "Nome alterado"
        mockMvc.perform(put("/customers/${customerSaved.id}")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(customerSaved)))
            .andExpect(status().isNoContent)
            .andReturn()

        // Assert
        val customerUpdated = customerRepository.findById(customerSaved.id!!).get()
        assertEquals(customerSaved.name, customerUpdated.name)
    }

    @Test
    fun `should delete specific customer`() {
        // Arrange
        val customer1 = buildCustomer()
        customerRepository.save(customer1)

        val customer2 = buildCustomer()
        customerRepository.save(customer2)

        val customer3 = buildCustomer()
        customerRepository.save(customer3)

        val customer4 = buildCustomer()
        customerRepository.save(customer4)

        // Act
        mockMvc.perform(delete("/customers/${customer3.id}"))
            .andExpect(status().isNoContent)
            .andReturn()

        // Assert
        val customers = customerRepository.findAll().filter { it.status == CustomerStatus.ATIVO }
        assertEquals(3, customers.size)
        assertFalse(customers.contains(customer3))
    }
}