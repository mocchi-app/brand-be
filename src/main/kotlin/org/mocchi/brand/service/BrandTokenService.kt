package org.mocchi.brand.service

import org.mocchi.brand.model.entity.InsertBrandToken
import org.mocchi.brand.repository.BrandTokenRepository
import org.springframework.stereotype.Service

@Service
class BrandTokenService(
    private val brandTokenRepository: BrandTokenRepository
) {

    suspend fun insertOrUpdateTokenForBrand(insertBrandToken: InsertBrandToken) =
        brandTokenRepository.getByBrandId(insertBrandToken.brandId)
            ?.also { brandTokenRepository.updateTokeForBrand(insertBrandToken) }
            ?: brandTokenRepository.saveTokenForBrandId(insertBrandToken)
}
