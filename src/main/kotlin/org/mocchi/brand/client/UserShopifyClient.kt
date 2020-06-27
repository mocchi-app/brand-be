package org.mocchi.brand.client

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import org.mocchi.brand.model.client.ShopifyUser
import org.springframework.stereotype.Component

@Component
class UserShopifyClient(
    private val httpClient: HttpClient
) {

    companion object {
        const val CURRENT_USER_URL = "/admin/api/2020-04/users/current.json"
    }

    suspend fun getCurrentUser(url: String, token: String): ShopifyUser =
        httpClient.get(CURRENT_USER_URL) {
            headers {
                header(ShopifyToken.TOKEN_HEADER, token)
            }
            url {
                host = url
                protocol = URLProtocol.HTTPS
            }
        }
}
