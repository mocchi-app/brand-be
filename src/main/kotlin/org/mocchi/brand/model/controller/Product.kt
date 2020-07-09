package org.mocchi.brand.model.controller

import org.mocchi.brand.model.client.Variants
import java.time.OffsetDateTime

data class Product(
    val id: Long,
    val brandId: Long,
    val shopifyId: Long,
    val title: String?,
    val bodyHtml: String?,
    val vendor: String?,
    val productType: String?,
    val createdAt: OffsetDateTime?,
    val handle: String?,
    val updatedAt: OffsetDateTime?,
    val publishedAt: OffsetDateTime?,
    val templateSuffix: String?,
    val publishedScope: String?,
    val tags: String?,
    val variants: List<Variants>?,
    val imageSrc: String?
)
