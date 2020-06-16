package org.mocchi.brand.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthBody(
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    val code: String
)
