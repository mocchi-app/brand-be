package org.mocchi.brand.repository

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.mocchi.brand.convert.BrandAfterInsertConverter
import org.mocchi.brand.convert.BrandWithTokenConverter
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.BrandWithToken
import org.mocchi.brand.model.entity.InsertBrand
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class BrandRepository(
    private val databaseClient: DatabaseClient,
    private val brandAfterInsertConverter: BrandAfterInsertConverter,
    private val brandWithTokenConverter: BrandWithTokenConverter
) {

    suspend fun addNewBrand(insertBrand: InsertBrand): Brand =
        databaseClient.insert()
            .into(InsertBrand::class.java)
            .using(insertBrand)
            .fetch()
            .awaitFirst()
            .let { brandAfterInsertConverter.convert(it) }

    suspend fun getByUrl(brandUrl: String): Brand? =
        databaseClient.select()
            .from(Brand::class.java)
            .matching(
                Criteria.where("b_url").`is`(brandUrl)
            )
            .fetch()
            .awaitFirstOrNull()

    suspend fun getByIdJoinWithToken(brandId: Long): BrandWithToken? =
        databaseClient.execute(
            """
            SELECT * FROM brand b JOIN brand_token bt on b.b_id = bt.t_b_id
            WHERE b.b_id = $brandId
        """.trimIndent()
        )
            .fetch()
            .first()
            .map { brandWithTokenConverter.convert(it) }
            .awaitFirstOrNull()
}
