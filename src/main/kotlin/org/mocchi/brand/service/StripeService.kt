package org.mocchi.brand.service

import org.mocchi.brand.client.StripeClient
import org.mocchi.brand.model.controller.IntentResponse
import org.mocchi.brand.model.entity.InsertBrandPayment
import org.springframework.stereotype.Service

@Service
class StripeService(
    private val stripeClient: StripeClient,
    private val brandPaymentService: BrandPaymentService
) {

    suspend fun setUpNewCustomer(
        brandId: Long,
        email: String,
        fullName: String,
        commission: Int
    ): IntentResponse =
        stripeClient.createCustomer(email, fullName)
            .let {
                Pair(it, stripeClient.createIntent(it.id))
            }
            .also {
                brandPaymentService.insertNewPayment(
                    InsertBrandPayment(
                        brandId = brandId,
                        customerId = it.first.id,
                        clientSecret = it.second.clientSecret,
                        commission = commission
                    )
                )
            }.let {
                IntentResponse(it.second.clientSecret)
            }


}
