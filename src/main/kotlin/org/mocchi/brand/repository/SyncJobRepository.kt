package org.mocchi.brand.repository

import org.mocchi.brand.model.entity.SyncJob
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SyncJobRepository(
    private val databaseClient: DatabaseClient
) {

    fun getNextJob() =
        databaseClient.select()
            .from(SyncJob::class.java)
            .matching(
                Criteria.where("sj_last_sync_date").lessThan(
                    LocalDateTime.now().minusDays(2)
                )
            )
            .fetch()
}
