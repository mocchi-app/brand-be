package org.mocchi.brand.service

import org.mocchi.brand.client.AuthShopifyClient
import org.springframework.stereotype.Service

@Service
class ShopifyService(
    private val authShopifyClient: AuthShopifyClient
) {

    suspend fun validateResponse(shop: String, code: String) =
        authShopifyClient.getAccessToken(shop, code)
}
