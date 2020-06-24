package org.mocchi.brand.repository

import org.mocchi.brand.convert.BrandTokenAfterInsertConverter
import org.mocchi.brand.convert.BrandWithTokenConverter
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.BrandWithToken
import org.mocchi.brand.model.entity.InsertBrandToken
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.r2dbc.core.awaitRowsUpdated
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class BrandTokenRepository(
    private val databaseClient: DatabaseClient,
    private val insertConverter: BrandTokenAfterInsertConverter,
    private val brandWithTokenConverter: BrandWithTokenConverter
) {

    suspend fun saveTokenForBrandId(insertBrandToken: InsertBrandToken) =
        databaseClient.insert()
            .into(InsertBrandToken::class.java)
            .using(insertBrandToken)
            .fetch()
            .awaitFirst()
            .let { insertConverter.convert(it) }

    suspend fun updateTokeForBrand(insertBrandToken: InsertBrandToken): Int =
        databaseClient.update()
            .table("brand_token")
            .using(
                Update.update("t_token", insertBrandToken.token)
                    .set("t_scope", insertBrandToken.scope)
                    .set("t_expires_in", insertBrandToken.expiresIn)
            )
            .matching(Criteria.where("t_b_id").`is`(insertBrandToken.brandId))
            .fetch()
            .awaitRowsUpdated()

    suspend fun getByBrandId(brandId: Long): BrandToken? =
        databaseClient.select()
            .from(BrandToken::class.java)
            .matching(
                Criteria.where("t_b_id").`is`(brandId)
            )
            .fetch()
            .awaitFirstOrNull()

    fun getBrandByToken(token: String): Mono<BrandWithToken> =
        databaseClient.execute(
            """
            SELECT * FROM brand b JOIN brand_token bt on b.b_id = bt.t_b_id
            WHERE bt.t_token = :token
        """.trimIndent()
        )
            .bind("token", token)
            .fetch()
            .first()
            .map { brandWithTokenConverter.convert(it) }
}
