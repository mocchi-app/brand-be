package org.mocchi.brand.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.mocchi.brand.convert.BrandAfterInsertConverter
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.InsertBrand
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class BrandRepository(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper,
    private val brandAfterInsertConverter: BrandAfterInsertConverter
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
}
