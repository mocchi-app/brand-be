package org.mocchi.brand.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class ShopifyUser(
    @JsonProperty("account_owner") val accountOwner: String,
    @JsonProperty("bio") val bio: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("id") val shopifyId: Long,
    @JsonProperty("im") val im: String,
    @JsonProperty("last_name") val lastName: String,
    @JsonProperty("permissions") val permissions: List<String>,
    @JsonProperty("phone") val phone: String,
    @JsonProperty("receive_announcements") val receiveAnnouncements: String,
    @JsonProperty("url") val url: String,
    @JsonProperty("locale") val locale: String,
    @JsonProperty("user_type") val userType: String
)
