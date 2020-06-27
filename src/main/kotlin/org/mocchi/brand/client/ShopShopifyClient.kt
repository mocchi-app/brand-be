package org.mocchi.brand.client

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import org.mocchi.brand.model.client.ShopifyShopResponse
import org.springframework.stereotype.Component

@Component
class ShopShopifyClient(
    private val httpClient: HttpClient
) {

    companion object {
        const val SHOP_INFO_URL = "/admin/api/2020-04/shop.json"
    }

    suspend fun getShop(url: String, token: String): ShopifyShopResponse =
        httpClient.get(SHOP_INFO_URL) {
            headers {
                header(ShopifyToken.TOKEN_HEADER, token)
            }
            url {
                host = url
                protocol = URLProtocol.HTTPS
            }
        }
}
