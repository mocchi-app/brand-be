package org.mocchi.brand.configuration

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository


@EnableWebFluxSecurity
class SecuityConfiguration {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        serverSecurityContextRepository: ServerSecurityContextRepository,
        reactiveAuthenticationManager: ReactiveAuthenticationManager
    ): SecurityWebFilterChain =
        http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .cors().disable()
            .logout().disable()
            .securityContextRepository(serverSecurityContextRepository)
            .authenticationManager(reactiveAuthenticationManager)
            .authorizeExchange()
            .pathMatchers(
                "/v2/api-docs",
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/favicon.ico"
            ).permitAll()
            .pathMatchers("/about").permitAll()
            .pathMatchers("/api/v1/brand/signup").permitAll()
            .pathMatchers("/api/v1/brand/complete").permitAll()
            .pathMatchers("/api/**/brand/**").hasRole("BRAND_ADMIN")
            .pathMatchers("/api/**/payment/**").hasRole("BRAND_ADMIN")
            .anyExchange().authenticated()
            .and()
            .build()
}
