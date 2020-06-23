package org.mocchi.brand.controller

import kotlinx.coroutines.flow.Flow
import org.mocchi.brand.model.controller.Product
import org.mocchi.brand.service.BrandProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brand")
class BrandProductController(
    private val brandProductService: BrandProductService
) {

    @GetMapping("/products")
    fun getAllProducts(): Flow<Product> =
        brandProductService.getAllProducts()
}
