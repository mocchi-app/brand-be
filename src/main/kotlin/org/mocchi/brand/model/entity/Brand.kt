package org.mocchi.brand.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("brand")
data class Brand(
    @Column("b_id")
    val id: Long,
    @Column("b_full_name")
    val fullName: String,
    @Column("b_url")
    val url: String,
    @Column("b_email")
    val email: String
)

@Table("brand")
data class InsertBrand(
    @Column("b_full_name")
    val fullName: String,
    @Column("b_url")
    val url: String,
    @Column("b_email")
    val email: String
)
