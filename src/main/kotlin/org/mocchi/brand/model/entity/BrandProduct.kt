package org.mocchi.brand.model.entity

import io.r2dbc.postgresql.codec.Json
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("brand_products")
data class BrandProduct(
    @Column("bp_id") val id: Long,
    @Column("bp_b_id") val brandId: Long,
    @Column("bp_shopify_id") val shopifyId: Long,
    @Column("bp_title") val title: String? = null,
    @Column("bp_body_html") val bodyHtml: String? = null,
    @Column("bp_vendor") val vendor: String? = null,
    @Column("bp_product_type") val productType: String? = null,
    @Column("bp_created_at") val createdAt: OffsetDateTime? = null,
    @Column("bp_handle") val handle: String? = null,
    @Column("bp_updated_at") val updatedAt: OffsetDateTime? = null,
    @Column("bp_published_at") val publishedAt: OffsetDateTime? = null,
    @Column("bp_template_suffix") val templateSuffix: String? = null,
    @Column("bp_published_scope") val publishedScope: String? = null,
    @Column("bp_tags") val tags: String? = null,
    @Column("bp_variants") val variants: Json?,
    @Column("bp_image_src") val imageSrc: String? = null,
    @Column("bp_approved") val approved: Boolean
)

@Table("brand_products")
data class InsertBrandProduct(
    @Column("bp_b_id") val brandId: Long,
    @Column("bp_shopify_id") val shopifyId: Long,
    @Column("bp_title") val title: String? = null,
    @Column("bp_body_html") val bodyHtml: String? = null,
    @Column("bp_vendor") val vendor: String? = null,
    @Column("bp_product_type") val productType: String? = null,
    @Column("bp_created_at") val createdAt: OffsetDateTime? = null,
    @Column("bp_handle") val handle: String? = null,
    @Column("bp_updated_at") val updatedAt: OffsetDateTime? = null,
    @Column("bp_published_at") val publishedAt: OffsetDateTime? = null,
    @Column("bp_template_suffix") val templateSuffix: String? = null,
    @Column("bp_published_scope") val publishedScope: String? = null,
    @Column("bp_tags") val tags: String? = null,
    @Column("bp_variants") val variants: Json? = null,
    @Column("bp_image_src") val imageSrc: String? = null
)
