package org.mocchi.brand.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    val scope: String,
    @JsonProperty("expires_in")
    val expiresIn: Long
)

data class AssociatedUser(
    private val id: Long,
    private val firstName: String,
    private val lastName: String,
    private val email: String
)


//{
//    "access_token": "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",
//    "scope": "write_orders,read_customers",
//    "expires_in": 86389,
//    "associated_user_scope": "write_orders,read_customers",
//    "session": "1b3f90f679a6ec13c16cf54b1f3d1fd092c0fb4fd3bbf9d0f9425b506309d593",
//    "associated_user": {
//        "id": 56105795734,
//        "first_name": "Igor",
//        "last_name": "Drozdov",
//        "email": "drozdovigor1996@gmail.com",
//        "account_owner": true,
//        "locale": "en",
//        "collaborator": false,
//        "email_verified": true
//    }
//}
