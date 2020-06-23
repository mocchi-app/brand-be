package org.mocchi.brand.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Component
@ConfigurationProperties(prefix = "oauth")
class OauthRedirectProperties {

    @NotBlank
    lateinit var serverRedirect: String

    @NotBlank
    lateinit var frontRedirect: String
}
