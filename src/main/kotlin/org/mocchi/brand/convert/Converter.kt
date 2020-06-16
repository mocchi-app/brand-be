package org.mocchi.brand.convert

interface Converter<T1, T2> {
    fun convert(source: T1): T2
}

inline fun <reified T : Any> extractFromResultSet(
    map: Map<String, Any>,
    field: String
): T =
    (map[field] ?: throw RuntimeException("$field doesn't exist"))
        .takeIf { it is T }
        .let { it as T }

