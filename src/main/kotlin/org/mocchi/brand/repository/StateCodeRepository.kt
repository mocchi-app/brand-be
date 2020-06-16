package org.mocchi.brand.repository

import org.mocchi.brand.convert.StateCodeConverter
import org.mocchi.brand.model.entity.InsertStateCode
import org.mocchi.brand.model.entity.StateCode
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

@Repository
class StateCodeRepository(
    private val databaseClient: DatabaseClient,
    private val insertConverter: StateCodeConverter
) {

    suspend fun saveNewCode(brandId: Long): StateCode =
        databaseClient.insert()
            .into(InsertStateCode::class.java)
            .using(InsertStateCode(brandId))
            .fetch()
            .awaitFirst()
            .let { insertConverter.convert(it) }

    suspend fun getById(id: Long): StateCode? =
        databaseClient.select()
            .from(StateCode::class.java)
            .matching(
                Criteria.where("sc_id").`is`(id)
            )
            .fetch()
            .awaitFirstOrNull()
}
