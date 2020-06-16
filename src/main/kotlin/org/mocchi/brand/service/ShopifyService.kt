package org.mocchi.brand.service

import org.mocchi.brand.client.ShopifyClient
import org.mocchi.brand.model.client.AuthBody
import org.springframework.stereotype.Service

@Service
class ShopifyService(
    private val shopifyClient: ShopifyClient
) {

    suspend fun validateResponse(shop: String, code: String) =
        shopifyClient.getAccessToken(shop, code)
}
