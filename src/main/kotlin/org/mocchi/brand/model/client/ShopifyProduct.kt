package org.mocchi.brand.model.client

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime
import java.util.*

data class GetAllProductsResponse(
    val products: List<ShopifyProduct>
)

data class ShopifyProduct(
    @JsonProperty("id") val shopifyId: Long,
    @JsonProperty("title") val title: String?,
    @JsonProperty("body_html") val bodyHtml: String?,
    @JsonProperty("vendor") val vendor: String?,
    @JsonProperty("product_type") val productType: String?,
    @JsonProperty("created_at") val createdAt: OffsetDateTime?,
    @JsonProperty("handle") val handle: String?,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime?,
    @JsonProperty("published_at") val publishedAt: OffsetDateTime?,
    @JsonProperty("template_suffix") val templateSuffix: String?,
    @JsonProperty("published_scope") val publishedScope: String?,
    @JsonProperty("tags") val tags: String?,
    @JsonProperty("variants") val variants: List<Variants>?
)

data class Variants(
    @JsonAnySetter
    @get:JsonAnyGetter
    val properties: Map<Any, Any> = HashMap()
)
