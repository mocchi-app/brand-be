package org.mocchi.brand.configuration

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository


@EnableWebFluxSecurity
class SecuityConfiguration {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        serverSecurityContextRepository: ServerSecurityContextRepository
    ): SecurityWebFilterChain =
        http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()
            .securityContextRepository(serverSecurityContextRepository)
            .authorizeExchange()
            .pathMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
            ).permitAll()
            .pathMatchers("/about").permitAll()
            .pathMatchers("/api/v1/brand/signup").permitAll()
            .pathMatchers("/api/v1/brand/complete").permitAll()
            .pathMatchers("/api/**/brand/**").hasRole("BRAND_ADMIN")
            .anyExchange().authenticated()
            .and()
            .build()
}