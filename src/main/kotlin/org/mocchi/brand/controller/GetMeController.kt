package org.mocchi.brand.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/brand")
class GetMeController {

    @GetMapping("/me")
    fun getMe(): Mono<Authentication> = ReactiveSecurityContextHolder.getContext().map { it.authentication }
}
