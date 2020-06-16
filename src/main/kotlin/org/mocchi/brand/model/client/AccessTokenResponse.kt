package org.mocchi.brand.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    val scope: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("associated_user")
    val associatedUser: AssociatedUser
)

data class AssociatedUser(
    private val id: Long,
    private val firstName: String,
    private val lastName: String,
    private val email: String
)
