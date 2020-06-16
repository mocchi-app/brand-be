package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.BrandToken
import org.springframework.stereotype.Component

@Component
class BrandTokenAfterInsertConverter : Converter<Map<String, Any>, BrandToken> {
    override fun convert(source: Map<String, Any>): BrandToken =
        BrandToken(
            id = extractFromResultSet(source, "t_id"),
            bId = extractFromResultSet(source, "t_b_id"),
            token = extractFromResultSet(source, "t_token"),
            scope = extractFromResultSet(source, "t_scope"),
            expiresIn = extractFromResultSet(source, "t_expires_in")
        )
}
