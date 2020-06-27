package org.mocchi.brand.service

import kotlinx.coroutines.flow.flow
import org.mocchi.brand.client.AuthShopifyClient
import org.mocchi.brand.client.ProductShopifyClient
import org.mocchi.brand.model.client.AccessTokenResponse
import org.springframework.stereotype.Service

@Service
class ShopifyService(
    private val authShopifyClient: AuthShopifyClient,
    private val productShopifyClient: ProductShopifyClient
) {

    suspend fun validateResponse(shop: String, code: String): AccessTokenResponse =
        authShopifyClient.getAccessToken(shop, code)

    suspend fun fetchAllProducts(url: String, token: String) =
        flow {
            var sinceId: Long? = null
            while (true) {
                val productsSince = productShopifyClient.getProductsSince(url, token, sinceId)
                if (productsSince.products.isEmpty()) {
                    break;
                }
                sinceId = productsSince.products.lastOrNull()?.shopifyId
                emit(productsSince.products)
            }
        }
}
