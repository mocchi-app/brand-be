package org.mocchi.brand.repository

import kotlinx.coroutines.reactive.awaitFirst
import org.mocchi.brand.convert.BrandPaymentConverter
import org.mocchi.brand.model.entity.BrandPayment
import org.mocchi.brand.model.entity.InsertBrandPayment
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class BrandPaymentRepository(
    private val databaseClient: DatabaseClient,
    private val brandPaymentConverter: BrandPaymentConverter
) {

    suspend fun insertNewPayment(insertBrandPayment: InsertBrandPayment): BrandPayment =
        databaseClient.insert()
            .into(InsertBrandPayment::class.java)
            .using(insertBrandPayment)
            .fetch()
            .first()
            .map { brandPaymentConverter.convert(it) }
            .awaitFirst()

    suspend fun findByBrandId(brandId: Long): BrandPayment? =
        databaseClient.select()
            .from(BrandPayment::class.java)
            .matching(Criteria.where("p_b_id").`is`(brandId))
            .fetch()
            .awaitFirstOrNull()
}
