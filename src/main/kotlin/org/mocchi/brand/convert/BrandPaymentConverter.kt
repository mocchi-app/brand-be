package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.BrandPayment
import org.springframework.stereotype.Component

@Component
class BrandPaymentConverter : Converter<Map<String, Any>, BrandPayment> {
    override fun convert(source: Map<String, Any>): BrandPayment =
        BrandPayment(
            id = extractFromResultSet(source, "p_id"),
            brandId = extractFromResultSet(source, "p_b_id"),
            customerId = extractFromResultSet(source, "p_customer_id"),
            clientSecret = extractFromResultSet(source, "p_client_secret"),
            commission = extractFromResultSet(source, "p_commission")
        )
}
