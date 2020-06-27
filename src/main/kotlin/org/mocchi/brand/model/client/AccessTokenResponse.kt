package org.mocchi.brand.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    val scope: String,
    @JsonProperty("expires_in")
    val expiresIn: Long?,
    @JsonProperty("associated_user")
    val associatedUser: AssociatedUser?
)

data class AssociatedUser(
    val id: Long,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    val email: String
)
