package org.mocchi.brand.service

import org.mocchi.brand.model.entity.BrandPayment
import org.mocchi.brand.model.entity.InsertBrandPayment
import org.mocchi.brand.repository.BrandPaymentRepository
import org.springframework.stereotype.Service

@Service
class BrandPaymentService(
    private val brandPaymentRepository: BrandPaymentRepository
) {
    suspend fun insertNewPayment(insertBrandPayment: InsertBrandPayment): BrandPayment =
        brandPaymentRepository.insertNewPayment(insertBrandPayment)

    suspend fun findByBrandId(brandId: Long): BrandPayment? =
        brandPaymentRepository.findByBrandId(brandId)
}
