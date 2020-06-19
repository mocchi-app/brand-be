package org.mocchi.brand.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("sync_job")
data class SyncJob(
    @Column("sj_id") val id: Long,
    @Column("sj_b_id") val brandId: Long,
    @Column("sj_last_sync_date") val lastSyncDate: LocalDateTime
)
