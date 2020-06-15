package org.mocchi.brand.controller

import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.service.SignUpService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/brand")
class SignUpController(
    private val signUpService: SignUpService
) {

    @PostMapping("/signup")
    fun signUpBrand(@RequestBody signUpDto: SignUpDto): Int =
        signUpService.signUpBrand(signUpDto)
}
