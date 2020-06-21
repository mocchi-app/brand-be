package org.mocchi.brand.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class SecurityContextRepository : ServerSecurityContextRepository {
    companion object {
        private const val TOKEN_PREFIX = "Bearer "
    }

    override fun save(swe: ServerWebExchange, sc: SecurityContext): Mono<Void>? {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(swe: ServerWebExchange): Mono<SecurityContext> =
        swe.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith(TOKEN_PREFIX) }
            ?.replace(TOKEN_PREFIX, "")
            ?.let { token ->
                Mono.just(
                    AnonymousAuthenticationToken(
                        token, token, listOf(SimpleGrantedAuthority("ROLE_BRAND_ADMIN"))
                    )
                        .also { it.isAuthenticated = true }
                )
                    .map { SecurityContextImpl(it) as SecurityContext }
            }
            ?: Mono.empty<SecurityContext>()
}
