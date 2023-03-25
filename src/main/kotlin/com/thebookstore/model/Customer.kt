package com.thebookstore.model

import com.thebookstore.enums.CustomerStatus
import com.thebookstore.enums.Role
import javax.persistence.*

@Entity(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column
    var password: String,

    @Column(name = "role")
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: Set<Role> = setOf()
)
