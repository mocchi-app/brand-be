package org.mocchi.brand.client

import com.stripe.model.Customer
import com.stripe.model.SetupIntent
import com.stripe.param.CustomerCreateParams
import org.springframework.stereotype.Component

@Component
class StripeClient {

    fun createCustomer(email: String, fullName: String): Customer =
        Customer.create(
            CustomerCreateParams.builder()
                .setEmail(email)
                .setName(fullName)
                .build()
        )

    fun createIntent(customerId: String): SetupIntent =
        mapOf("customer" to customerId)
            .let {
                SetupIntent.create(it)
            }
}
