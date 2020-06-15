package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
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
            val expected = Brand(
                id = 1,
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val actual = brandRepository.addNewBrand(signUpDto)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }
}
