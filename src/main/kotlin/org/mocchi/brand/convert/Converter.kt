package org.mocchi.brand.convert

interface Converter<T1, T2> {
    fun convert(source: T1): T2
}
