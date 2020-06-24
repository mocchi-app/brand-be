package org.mocchi.brand.security

import org.mocchi.brand.model.entity.BrandWithToken
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class BrandAuthenticatorManager : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> =
        authentication
            .also {
                if (it.principal is BrandWithToken) {
                    it.isAuthenticated = true
                }
            }
            .let { Mono.just(it) }
}
