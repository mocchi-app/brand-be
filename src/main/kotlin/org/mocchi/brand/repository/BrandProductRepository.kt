package org.mocchi.brand.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.asFlux
import org.mocchi.brand.convert.BrandProductConverter
import org.mocchi.brand.model.entity.BrandProduct
import org.mocchi.brand.model.entity.InsertBrandProduct
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class BrandProductRepository(
    private val databaseClient: DatabaseClient,
    private val brandProductConverter: BrandProductConverter
) {

    fun insertBrandProduct(brandProducts: List<InsertBrandProduct>): Flow<BrandProduct> =
        brandProducts.joinToString(",") {
            """
                (
                    ${it.brandId}, ${it.shopifyId},
                    ${nullOrString(it.title)}, ${nullOrString(it.bodyHtml)},
                    ${nullOrString(it.vendor)}, ${nullOrString(it.productType)},
                    ${nullOrString(it.createdAt?.toString())}, ${nullOrString(it.handle)},
                    ${nullOrString(it.updatedAt?.toString())}, ${nullOrString(it.publishedAt?.toString())},
                    ${nullOrString(it.templateSuffix)}, ${nullOrString(it.publishedScope)},
                    ${nullOrString(it.tags)}, ${nullOrString(it.variants?.asString())}, ${nullOrString(it.imageSrc)}
                )
            """.trimIndent()
        }
            .let {
                databaseClient.execute(
                    """
                        INSERT INTO brand_products (bp_b_id, bp_shopify_id, bp_title, bp_body_html, bp_vendor,
                                                    bp_product_type, bp_created_at, bp_handle, bp_updated_at,
                                                    bp_published_at, bp_template_suffix, bp_published_scope,
                                                    bp_tags, bp_variants, bp_image_src)
                        VALUES $it
                        ON CONFLICT (bp_b_id, bp_shopify_id) DO UPDATE SET
                           bp_b_id = EXCLUDED.bp_b_id,
                           bp_shopify_id = EXCLUDED.bp_shopify_id,
                           bp_title = EXCLUDED.bp_title,
                           bp_body_html = EXCLUDED.bp_body_html,
                           bp_vendor = EXCLUDED.bp_vendor,
                           bp_product_type = EXCLUDED.bp_product_type,
                           bp_created_at = EXCLUDED.bp_created_at,
                           bp_handle = EXCLUDED.bp_handle,
                           bp_updated_at = EXCLUDED.bp_updated_at,
                           bp_published_at = EXCLUDED.bp_published_at,
                           bp_template_suffix = EXCLUDED.bp_template_suffix,
                           bp_published_scope = EXCLUDED.bp_published_scope,
                           bp_tags = EXCLUDED.bp_tags,
                           bp_variants = EXCLUDED.bp_variants,
                           bp_image_src = EXCLUDED.bp_image_src
                        RETURNING *
                    """.trimIndent()
                )
                    .fetch()
                    .all()
                    .asFlow()
                    .map {
                        brandProductConverter.convert(it)
                    }
            }

    fun selectAll(): Flow<BrandProduct> =
        databaseClient.select()
            .from(BrandProduct::class.java)
            .orderBy(Sort.by("bp_created_at"))
            .fetch()
            .all()
            .asFlow()

    private fun nullOrString(nullable: String?) =
        nullable?.let { "'$nullable'" } ?: "null"

    suspend fun updateApproved(id: Long, brandId: Long, approved: Boolean): Int =
        databaseClient.update()
            .table("brand_products")
            .using(Update.update("bp_approved", approved))
            .matching(
                Criteria.where("bp_id").`is`(id).and(
                    Criteria.where("bp_b_id").`is`(brandId)
                )
            )
            .fetch()
            .rowsUpdated()
            .awaitFirst()
}
