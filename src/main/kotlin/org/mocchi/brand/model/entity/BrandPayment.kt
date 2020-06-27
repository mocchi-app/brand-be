package org.mocchi.brand.model.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("brand_payment")
data class BrandPayment(
    @Column("p_id") val id: Long,
    @Column("p_b_id") val brandId: Long,
    @Column("p_customer_id") val customerId: String,
    @Column("p_client_secret") val clientSecret: String,
    @Column("p_commission") val commission: Int
)

@Table("brand_payment")
data class InsertBrandPayment(
    @Column("p_b_id") val brandId: Long,
    @Column("p_customer_id") val customerId: String,
    @Column("p_client_secret") val clientSecret: String,
    @Column("p_commission") val commission: Int
)
