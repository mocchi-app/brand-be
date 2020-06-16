package org.mocchi.brand.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("state_code")
data class StateCode(
    @Column("sc_id")
    val id: Long,
    @Column("sc_url")
    val url: String
)

@Table("state_code")
data class InsertStateCode(
    @Column("sc_url")
    val url: String
)
