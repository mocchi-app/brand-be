package org.mocchi.brand.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mocchi.brand.convert.ProductConverter
import org.mocchi.brand.model.controller.Product
import org.mocchi.brand.repository.BrandProductRepository
import org.springframework.stereotype.Service

@Service
class BrandProductService(
    private val brandProductRepository: BrandProductRepository,
    private val productConverter: ProductConverter
) {

    fun getAllProducts(): Flow<Product> =
        brandProductRepository.selectAll()
            .map { productConverter.convert(it) }
}
