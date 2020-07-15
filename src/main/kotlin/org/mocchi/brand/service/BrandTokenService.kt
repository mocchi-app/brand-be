package org.mocchi.brand.service

import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.FullBrand
import org.mocchi.brand.model.entity.InsertBrandToken
import org.mocchi.brand.repository.BrandTokenRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BrandTokenService(
    private val brandTokenRepository: BrandTokenRepository
) {

    fun getBrandByToken(token: String): Mono<FullBrand> =
        brandTokenRepository.getBrandByToken(token)

    suspend fun insertOrUpdateTokenForBrand(insertBrandToken: InsertBrandToken): BrandToken =
        brandTokenRepository.getByBrandId(insertBrandToken.brandId)
            ?.also { brandTokenRepository.updateTokeForBrand(insertBrandToken) }
            ?: brandTokenRepository.saveTokenForBrandId(insertBrandToken)
}
