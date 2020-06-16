package org.mocchi.brand.controller

import org.mocchi.brand.model.client.AuthBody
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.service.ShopifyService
import org.mocchi.brand.service.SignUpService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brand")
class SignUpController(
    private val signUpService: SignUpService,
    private val shopifyService: ShopifyService
) {

    @PostMapping("/signup")
    suspend fun signUpBrand(@RequestBody signUpDto: SignUpDto): Int =
        signUpService.signUpBrand(signUpDto)

    @GetMapping("/complete")
    suspend fun completeAuthentication(
        @RequestParam("code") code: String,
        @RequestParam("hmac") hmac: String,
        @RequestParam("shop") shop: String,
        @RequestParam("state") state: Int
    ) =
        shopifyService.validateResponse(shop, code)
}
