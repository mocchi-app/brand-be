package org.mocchi.brand.service

import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.InsertBrandToken
import org.mocchi.brand.repository.BrandRepository
import org.mocchi.brand.repository.BrandTokenRepository
import org.mocchi.brand.repository.StateCodeRepository
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val brandRepository: BrandRepository,
    private val stateCodeRepository: StateCodeRepository,
    private val brandTokenRepository: BrandTokenRepository,
    private val shopifyService: ShopifyService
) {
    suspend fun signUpBrand(signUpDto: SignUpDto): Long =
        insertOrFindBrand(signUpDto)
            .let { stateCodeRepository.saveNewCode(it.id).id }

    suspend fun completeLogin(shop: String, code: String, state: Long) =
        stateCodeRepository.getById(state)
            ?.let { stateCode ->
                shopifyService.validateResponse(shop, code)
                    .let { tokenResponse ->
                        brandTokenRepository.saveTokenForBrandId(
                            InsertBrandToken(
                                stateCode.brandId,
                                tokenResponse.accessToken,
                                tokenResponse.scope,
                                tokenResponse.expiresIn
                            )
                        )
                    }
            }
            ?: throw RuntimeException("Unable to find code")

    private suspend fun insertOrFindBrand(signUpDto: SignUpDto): Brand =
        brandRepository.getByUrl(signUpDto.companyUrl) ?: brandRepository.addNewBrand(signUpDto)

}
