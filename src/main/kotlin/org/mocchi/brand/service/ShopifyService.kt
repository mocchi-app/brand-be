package org.mocchi.brand.service

import org.mocchi.brand.client.AuthShopifyClient
import org.mocchi.brand.client.ProductShopifyClient
import org.springframework.stereotype.Service

@Service
class ShopifyService(
    private val authShopifyClient: AuthShopifyClient,
    private val productShopifyClient: ProductShopifyClient
) {

    suspend fun validateResponse(shop: String, code: String) =
        authShopifyClient.getAccessToken(shop, code)

    suspend fun fetchAllProducts(url: String, token: String) =
        productShopifyClient.getProductsSince(url, token, null)
}
