package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.Brand
import org.springframework.stereotype.Component

@Component
class BrandAfterInsertConverter : Converter<Map<String, Any>, Brand> {
    override fun convert(source: Map<String, Any>): Brand =
        Brand(
            id = extractFromResultSet(source, "b_id"),
            fullName = extractFromResultSet(source, "b_full_name"),
            url = extractFromResultSet(source, "b_url"),
            email = extractFromResultSet(source, "b_email")
        )
}
