package org.mocchi.brand.convert

import org.mocchi.brand.model.entity.StateCode
import org.springframework.stereotype.Component

@Component
class StateCodeConverter : Converter<Map<String, Any>, StateCode> {
    override fun convert(source: Map<String, Any>): StateCode =
        StateCode(
            id = extractFromResultSet(source, "sc_id"),
            url = extractFromResultSet(source, "sc_url")
        )
}
