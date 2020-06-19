package org.mocchi.brand.model.entity

data class BrandWithToken(
    val brandId: Long,
    val fullName: String,
    val url: String,
    val email: String,
    val token: String,
    val scope: String,
    val expiresIn: Long
)
