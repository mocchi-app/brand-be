package org.mocchi.brand.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("brand_token")
data class BrandToken(
    @Column("t_id")
    @Id
    val id: Long,
    @Column("t_b_id")
    val bId: Long,
    @Column("t_token")
    val token: String,
    @Column("t_scope")
    val scope: String,
    @Column("t_expires_in")
    val expiresIn: Long
)


@Table("brand_token")
data class InsertBrandToken(
    @Column("t_b_id")
    val brandId: Long,
    @Column("t_token")
    val token: String,
    @Column("t_scope")
    val scope: String,
    @Column("t_expires_in")
    val expiresIn: Long
)
