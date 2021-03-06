package org.mocchi.brand.service

import org.mocchi.brand.client.ShopShopifyClient
import org.mocchi.brand.configuration.OauthRedirectProperties
import org.mocchi.brand.model.client.AccessTokenResponse
import org.mocchi.brand.model.client.ShopifyShopResponse
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.BrandToken
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
    private val brandSyncJobService: BrandSyncJobService,
    private val oauthRedirectProperties: OauthRedirectProperties,
    private val shopShopifyClient: ShopShopifyClient
) {
    suspend fun signUpBrand(signUpDto: SignUpDto): String =
        stateCodeRepository.saveNewCode(signUpDto.companyUrl).id
            .let {
                "https://${signUpDto.companyUrl}/admin/oauth/authorize" +
                        "?client_id=d1573479b208211e6c983a2688891523" +
                        "&scope=read_products" +
                        "&redirect_uri=${oauthRedirectProperties.serverRedirect}/api/v1/brand/complete" +
                        "&state=$it"
            }

    suspend fun completeLogin(shopUrl: String, code: String, state: Long): BrandToken =
        stateCodeRepository.getById(state)
            ?.takeIf { it.url == shopUrl }
            ?.let {
                shopifyService.validateResponse(shopUrl, code)
                    .let { tokenResponse ->
                        val shopResponse = shopShopifyClient.getShop(it.url, tokenResponse.accessToken)

                        val brand = brandService.insertOrFindBrand(
                            insertBrand(shopResponse, shopUrl)
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
        tokenResponse.scope
    )

    private fun insertBrand(
        shopResponse: ShopifyShopResponse,
        shopUrl: String
    ): InsertBrand =
        InsertBrand(
            shopResponse.shop.name,
            shopUrl,
            shopResponse.shop.email
        )

}
