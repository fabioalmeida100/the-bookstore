package com.thebookstore.service

import com.thebookstore.model.PurchaseModel
import com.thebookstore.repository.PurchaseRepository
import org.springframework.stereotype.Service

@Service
class PurchaseService(private val purchaseRepository: PurchaseRepository) {
    fun create(purchase: PurchaseModel) {
        purchaseRepository.save(purchase)
    }
}
