package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.FullBrand
import org.mocchi.brand.model.entity.Payment
import org.mocchi.brand.model.entity.Token
import org.springframework.stereotype.Component

@Component
class BrandWithTokenConverter : Converter<Map<String, Any>, FullBrand> {

    override fun convert(source: Map<String, Any>): FullBrand =
        FullBrand(
            brandId = extractFromResultSet(source, "b_id"),
            fullName = extractFromResultSet(source, "b_full_name"),
            url = extractFromResultSet(source, "b_url"),
            email = extractFromResultSet(source, "b_email"),
            token = Token(
                token = extractFromResultSet(source, "t_token"),
                scope = extractFromResultSet(source, "t_scope")
            ),
            payment = source["p_client_secret"]
                .takeIf { it != null }
                ?.let {
                    Payment(
                        clientSecret = extractFromResultSet(source, "p_client_secret"),
                        commission = extractFromResultSet(source, "p_commission")
                    )
                }
        )
}
