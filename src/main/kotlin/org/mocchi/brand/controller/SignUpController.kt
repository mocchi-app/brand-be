package org.mocchi.brand.controller

import org.mocchi.brand.model.client.AuthBody
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.service.ShopifyService
import org.mocchi.brand.service.SignUpService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brand")
class SignUpController(
    private val signUpService: SignUpService
) {

    @PostMapping("/signup")
    suspend fun signUpBrand(@RequestBody signUpDto: SignUpDto): String =
        signUpService.signUpBrand(signUpDto)
            .let {
                "https://${signUpDto.companyUrl}/admin/oauth/authorize" +
                        "?client_id=d1573479b208211e6c983a2688891523" +
                        "&scope=read_customers,read_products" +
                        "&redirect_uri=http://localhost:8080/api/v1/brand/complete" +
                        "&state=$it" +
                        "&grant_options[]=per-user"
            }

    @GetMapping("/complete")
    suspend fun completeAuthentication(
        @RequestParam("code") code: String,
        @RequestParam("hmac") hmac: String,
        @RequestParam("shop") shop: String,
        @RequestParam("state") state: Long
    ) =
        signUpService.completeLogin(shop, code, state)
}
