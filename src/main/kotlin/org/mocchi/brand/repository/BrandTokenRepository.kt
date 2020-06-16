package org.mocchi.brand.repository

import org.mocchi.brand.convert.BrandTokenAfterInsertConverter
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.InsertBrandToken
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class BrandTokenRepository(
    private val databaseClient: DatabaseClient,
    private val insertConverter: BrandTokenAfterInsertConverter
) {

    suspend fun saveTokenForBrandId(insertBrandToken: InsertBrandToken) =
        databaseClient.insert()
            .into(InsertBrandToken::class.java)
            .using(insertBrandToken)
            .fetch()
            .awaitFirst()
            .let { insertConverter.convert(it) }

    suspend fun getByBrandId(brandId: Long): BrandToken? =
        databaseClient.select()
            .from(BrandToken::class.java)
            .matching(
                Criteria.where("t_b_id").`is`(brandId)
            )
            .fetch()
            .awaitFirstOrNull()
}
