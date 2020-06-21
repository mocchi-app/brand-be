package org.mocchi.brand.service

import org.mocchi.brand.model.client.AccessTokenResponse
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.InsertBrand
import org.mocchi.brand.model.entity.InsertBrandToken
import org.mocchi.brand.repository.StateCodeRepository
import org.mocchi.brand.schedule.BrandSyncJobService
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val brandService: BrandService,
    private val brandTokenService: BrandTokenService,
    private val stateCodeRepository: StateCodeRepository,
    private val shopifyService: ShopifyService,
    private val brandSyncJobService: BrandSyncJobService
) {
    suspend fun signUpBrand(signUpDto: SignUpDto): Long =
        stateCodeRepository.saveNewCode(signUpDto.companyUrl).id

    suspend fun completeLogin(shop: String, code: String, state: Long) =
        stateCodeRepository.getById(state)
            ?.takeIf { it.url == shop }
            ?.let {
                shopifyService.validateResponse(shop, code)
                    .let { tokenResponse ->
                        val brand = brandService.insertOrFindBrand(
                            insertBrand(tokenResponse, shop)
                        )
                        brandTokenService.insertOrUpdateTokenForBrand(
                            insertBrandToken(brand, tokenResponse)
                        )
                    }
            }
            ?.also {
                brandSyncJobService.startSyncProcessForBrand(it.id)
            }
            ?: throw RuntimeException("Unable to find code")

    private fun insertBrandToken(
        brand: Brand, tokenResponse: AccessTokenResponse
    ): InsertBrandToken = InsertBrandToken(
        brand.id,
        tokenResponse.accessToken,
        tokenResponse.scope,
        tokenResponse.expiresIn
    )

    private fun insertBrand(
        tokenResponse: AccessTokenResponse,
        shop: String
    ): InsertBrand =
        InsertBrand(
            "${tokenResponse.associatedUser.firstName} ${tokenResponse.associatedUser.lastName}",
            shop,
            tokenResponse.associatedUser.email
        )

}
