package com.thebookstore.model

import com.thebookstore.enums.BookStatus
import com.thebookstore.enums.Errors
import com.thebookstore.exception.BadRequestException
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {
    constructor(id: Int? = null, name: String, price: BigDecimal, status: BookStatus, customer: Customer? = null) :
        this(id, name, price, customer) {
            this.status = status
        }

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.CANCELADO || field == BookStatus.DELETADO) {
                throw BadRequestException(Errors.ML102.message.format(field), Errors.ML102.code)
            }

            field = value
        }
}
