package org.mocchi.brand.controller

import kotlinx.coroutines.flow.Flow
import org.mocchi.brand.model.controller.ApprovedResponse
import org.mocchi.brand.model.controller.Product
import org.mocchi.brand.model.entity.FullBrand
import org.mocchi.brand.service.BrandProductService
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/brand")
class BrandProductController(
    private val brandProductService: BrandProductService
) {

    @GetMapping("/products")
    fun getAllProducts(): Flow<Product> =
        brandProductService.getAllProducts()

    @PutMapping("/products/{productId}/approve")
    suspend fun approveProduct(principal: Principal, @PathVariable("productId") id: Long): ApprovedResponse =
        principal.let { it as AnonymousAuthenticationToken }
            .let { it.principal as FullBrand }
            .let { brandProductService.approveProduct(id, it.brandId) }

    @PutMapping("/products/{productId}/decline")
    suspend fun declineProduct(principal: Principal, @PathVariable("productId") id: Long): ApprovedResponse =
        principal.let { it as AnonymousAuthenticationToken }
            .let { it.principal as FullBrand }
            .let { brandProductService.declineProduct(id, it.brandId) }
}
