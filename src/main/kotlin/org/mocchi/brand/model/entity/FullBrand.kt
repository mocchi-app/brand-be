package org.mocchi.brand.model.entity

data class FullBrand(
    val brandId: Long,
    val fullName: String,
    val url: String,
    val email: String,
    val token: Token,
    val payment: Payment?
)

data class Token(
    val token: String,
    val scope: String
)

data class Payment(
    val clientSecret: String,
    val commission: Int
)
