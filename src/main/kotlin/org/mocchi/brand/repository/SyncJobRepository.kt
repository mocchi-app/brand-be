package org.mocchi.brand.repository

import kotlinx.coroutines.reactive.awaitFirst
import org.mocchi.brand.convert.SyncJobConverter
import org.mocchi.brand.model.entity.InsertSyncJob
import org.mocchi.brand.model.entity.SyncJob
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SyncJobRepository(
    private val databaseClient: DatabaseClient,
    private val syncJobConverter: SyncJobConverter
) {

    suspend fun insertNewBrand(insertSyncJob: InsertSyncJob): SyncJob =
        databaseClient.insert()
            .into(InsertSyncJob::class.java)
            .using(insertSyncJob)
            .fetch()
            .one()
            .map { syncJobConverter.convert(it) }
            .awaitFirst()

    suspend fun getNextJob(): SyncJob? =
        databaseClient.select()
            .from(SyncJob::class.java)
            .matching(
                Criteria.where("sj_last_sync_date").lessThan(
                    LocalDateTime.now().minusDays(2)
                )
            )
            .fetch()
            .awaitFirstOrNull()
}
