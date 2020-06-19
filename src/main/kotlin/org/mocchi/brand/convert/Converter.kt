package org.mocchi.brand.convert

interface Converter<T1, T2> {
    fun convert(source: T1): T2
}

inline fun <reified T> extractFromResultSet(
    map: Map<String, Any>,
    field: String
): T =
    (map.containsKey(field).takeIf { it } ?: throw RuntimeException("$field doesn't exist"))
        .let {
            map[field]
                .takeIf { it is T }
                .let { it as T }
        }

