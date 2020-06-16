package org.mocchi.brand.client

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol.Companion.HTTPS
import org.mocchi.brand.model.client.AccessTokenResponse
import org.mocchi.brand.model.client.AuthBody
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Component
class AuthShopifyClient(
    private val httpClient: HttpClient,
    private val shopifyProperties: ShopifyProperties
) {

    companion object {
        const val TOKEN_URL = "admin/oauth/access_token"
    }

    suspend fun getAccessToken(shop: String, code: String) =
        httpClient.post<AccessTokenResponse> {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = AuthBody(
                clientId = shopifyProperties.clientId,
                clientSecret = shopifyProperties.clientSecret,
                code = code
            )
            url {
                path(TOKEN_URL)
                host = shop
                protocol = HTTPS
            }
        }
}

@Validated
@Component
@ConfigurationProperties(prefix = "shopify")
class ShopifyProperties {

    @NotBlank
    lateinit var clientId: String

    @NotBlank
    lateinit var clientSecret: String

}
