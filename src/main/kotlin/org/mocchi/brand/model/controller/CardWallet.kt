package org.mocchi.brand.model.controller

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class CardWallet(
    @Min(1)
    @Max(99)
    val commission: Int
)
