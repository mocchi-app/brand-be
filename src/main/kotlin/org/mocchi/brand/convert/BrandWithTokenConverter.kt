package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.BrandWithToken
import org.springframework.stereotype.Component

@Component
class BrandWithTokenConverter : Converter<Map<String, Any>, BrandWithToken> {
    override fun convert(source: Map<String, Any>): BrandWithToken =
        BrandWithToken(
            brandId = extractFromResultSet(source, "b_id"),
            fullName = extractFromResultSet(source, "b_full_name"),
            url = extractFromResultSet(source, "b_url"),
            email = extractFromResultSet(source, "b_email"),
            token = extractFromResultSet(source, "t_token"),
            scope = extractFromResultSet(source, "t_scope"),
            expiresIn = extractFromResultSet(source, "t_expires_in")
        )
}
