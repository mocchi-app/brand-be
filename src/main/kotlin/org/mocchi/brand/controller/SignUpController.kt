package org.mocchi.brand.controller

import org.mocchi.brand.configuration.OauthRedirectProperties
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.controller.SignUpResponse
import org.mocchi.brand.service.SignUpService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brand")
class SignUpController(
    private val signUpService: SignUpService,
    private val oauthRedirectProperties: OauthRedirectProperties
) {

    @PostMapping("/signup")
    suspend fun signUpBrand(@RequestBody signUpDto: SignUpDto): SignUpResponse =
        SignUpResponse(
            signUpUrl = signUpService.signUpBrand(signUpDto)
        )

    @GetMapping("/complete")
    suspend fun completeAuthentication(
        @RequestParam("code") code: String,
        @RequestParam("hmac") hmac: String,
        @RequestParam("shop") shop: String,
        @RequestParam("state") state: Long,
        response: ServerHttpResponse
    ): ResponseEntity<Any> =
        signUpService.completeLogin(shop, code, state)
            .let {
                ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header("location", "${oauthRedirectProperties.frontRedirect}/complete?token=${it.token}")
                    .build<Any>()
            }
}
