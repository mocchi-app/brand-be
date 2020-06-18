package org.mocchi.brand.client

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import org.mocchi.brand.model.client.Count
import org.springframework.stereotype.Component

@Component
class ProductShopifyClient(
    private val httpClient: HttpClient
) {

    companion object {
        const val COUNT_URL = "/admin/api/2020-04/products/count.json"
        const val TOKEN_HEADER = "X-Shopify-Access-Token"
    }

    suspend fun countProducts(url: String, token: String) =
        httpClient.get<Count>(COUNT_URL) {
            headers {
                header(TOKEN_HEADER, token)
            }
            url {
                host = url
                protocol = URLProtocol.HTTPS
            }
        }
}
