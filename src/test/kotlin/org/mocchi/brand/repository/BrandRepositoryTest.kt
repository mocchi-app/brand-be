package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.controller.SignUpDto
import org.springframework.beans.factory.annotation.Autowired

internal class BrandRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Test
    fun `should insert new brand`() {
        runBlocking {
            val signUpDto = SignUpDto(
                companyName = "companyName",
                companyUrl = "companyUrl",
                companyEmail = "companyEmail"
            )
            val res = brandRepository.addNewBrand(signUpDto)
            println(res)
        }
    }
}
