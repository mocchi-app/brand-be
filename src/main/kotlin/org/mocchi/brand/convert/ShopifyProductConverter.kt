package org.mocchi.brand.convert

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import org.mocchi.brand.model.client.ShopifyProduct
import org.mocchi.brand.model.entity.InsertBrandProduct
import org.springframework.stereotype.Component

@Component
class ShopifyProductConverter(
    private val objectMapper: ObjectMapper
) : BiConverter<Long, ShopifyProduct, InsertBrandProduct> {

    override fun convert(brandId: Long, source: ShopifyProduct): InsertBrandProduct = InsertBrandProduct(
        brandId = brandId,
        shopifyId = source.shopifyId,
        title = source.title,
        bodyHtml = source.bodyHtml,
        vendor = source.vendor,
        productType = source.productType,
        createdAt = source.createdAt,
        handle = source.handle,
        updatedAt = source.updatedAt,
        publishedAt = source.publishedAt,
        templateSuffix = source.templateSuffix,
        publishedScope = source.publishedScope,
        tags = source.tags,
        variants = objectMapper.writeValueAsString(source.variants).let { Json.of(it) },
        imageSrc = source.image?.src
    )

}
