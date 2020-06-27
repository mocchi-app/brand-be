package org.mocchi.brand.controller

import org.mocchi.brand.model.controller.CardWallet
import org.mocchi.brand.model.controller.IntentResponse
import org.mocchi.brand.model.entity.BrandWithToken
import org.mocchi.brand.service.StripeService
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/payment")
class PaymentController(
    private val stripeService: StripeService
) {

    @PostMapping("/card")
    suspend fun createCardWallet(
        @RequestBody cardWallet: CardWallet,
        principal: Principal
    ): IntentResponse =
        principal
            .let { it as AnonymousAuthenticationToken }
            .let { it.principal as BrandWithToken }
            .let {
                stripeService.setUpNewCustomer(
                    it.brandId,
                    it.email,
                    it.fullName,
                    cardWallet.commission
                )
            }
}
