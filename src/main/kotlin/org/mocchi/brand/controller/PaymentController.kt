package org.mocchi.brand.controller

import org.mocchi.brand.model.controller.CardWallet
import org.mocchi.brand.model.controller.IntentResponse
import org.mocchi.brand.model.entity.FullBrand
import org.mocchi.brand.service.BrandPaymentService
import org.mocchi.brand.service.StripeService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/payment")
class PaymentController(
    private val stripeService: StripeService,
    private val brandPaymentService: BrandPaymentService
) {

    @GetMapping("/card")
    suspend fun getCurrentIntent(principal: Principal): ResponseEntity<IntentResponse> =
        principal.let { it as AnonymousAuthenticationToken }
            .let { it.principal as FullBrand }
            .let { brandPaymentService.findByBrandId(it.brandId) }
            ?.let { IntentResponse(it.clientSecret) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/card")
    suspend fun createCardWallet(
        @RequestBody cardWallet: CardWallet,
        principal: Principal
    ): IntentResponse =
        principal
            .let { it as AnonymousAuthenticationToken }
            .let { it.principal as FullBrand }
            .let {
                stripeService.setUpNewCustomer(
                    it.brandId,
                    it.email,
                    it.fullName,
                    cardWallet.commission
                )
            }
}
