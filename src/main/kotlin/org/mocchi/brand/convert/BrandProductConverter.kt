package org.mocchi.brand.convert

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.mocchi.brand.model.client.Variants
import org.mocchi.brand.model.controller.Product
import org.mocchi.brand.model.entity.BrandProduct
import org.springframework.stereotype.Component

@Component
class BrandProductConverter : Converter<Map<String, Any>, BrandProduct> {
    override fun convert(source: Map<String, Any>): BrandProduct =
        BrandProduct(
            id = extractFromResultSet(source, "bp_id"),
            brandId = extractFromResultSet(source, "bp_b_id"),
            shopifyId = extractFromResultSet(source, "bp_shopify_id"),
            title = extractFromResultSet(source, "bp_title"),
            bodyHtml = extractFromResultSet(source, "bp_body_html"),
            vendor = extractFromResultSet(source, "bp_vendor"),
            productType = extractFromResultSet(source, "bp_product_type"),
            createdAt = extractFromResultSet(source, "bp_created_at"),
            handle = extractFromResultSet(source, "bp_handle"),
            updatedAt = extractFromResultSet(source, "bp_updated_at"),
            publishedAt = extractFromResultSet(source, "bp_published_at"),
            templateSuffix = extractFromResultSet(source, "bp_template_suffix"),
            publishedScope = extractFromResultSet(source, "bp_published_scope"),
            tags = extractFromResultSet(source, "bp_tags"),
            variants = extractFromResultSet(source, "bp_variants")
        )
}

@Component
class ProductConverter(
    private val objectMapper: ObjectMapper
) : Converter<BrandProduct, Product> {
    override fun convert(source: BrandProduct): Product =
        Product(
            id = source.id,
            brandId = source.brandId,
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
            variants = source.variants?.let {
                objectMapper.readValue<List<Variants>>(it.asString())
            }
        )

}
