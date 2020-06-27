package org.mocchi.brand.configuration

import com.stripe.Stripe
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.annotation.PostConstruct
import javax.validation.constraints.NotBlank

@Configuration
class StripeConfiguration(
    private val stripeProperties: StripeProperties
) {

    @PostConstruct
    fun initStripe() {
        Stripe.apiKey = stripeProperties.apiKey
    }
}

@Validated
@Component
@ConfigurationProperties(prefix = "stripe")
class StripeProperties {

    @NotBlank
    lateinit var apiKey: String
}
