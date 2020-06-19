package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.SyncJob
import org.springframework.stereotype.Component

@Component
class SyncJobConverter : Converter<Map<String, Any>, SyncJob> {
    override fun convert(source: Map<String, Any>): SyncJob = SyncJob(
        id = extractFromResultSet(source, "sj_id"),
        brandId = extractFromResultSet(source, "sj_b_id"),
        lastSyncDate = extractFromResultSet(source, "sj_last_sync_date")
    )
}
