package org.mocchi.brand.convert

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
