package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.InsertBrandToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitRowsUpdated

internal class BrandTokenRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var brandTokenRepository: BrandTokenRepository

    @Test
    fun `should insert new brand`() {
        runBlocking {
            val signUpDto = SignUpDto(
                companyName = "companyName",
                companyUrl = "companyUrl",
                companyEmail = "companyEmail"
            )
            val brand = brandRepository.addNewBrand(signUpDto)

            val insertToken = InsertBrandToken(
                bId = brand.id,
                token = "token",
                scope = "scope",
                expiresIn = 1000
            )
            val expected = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope",
                expiresIn = 1000
            )
            val actual = brandTokenRepository.saveTokenForBrandId(insertToken)
            Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }

    @Test
    fun `should find existing brand`() {
        runBlocking {
            val signUpDto = SignUpDto(
                companyName = "companyName",
                companyUrl = "companyUrl",
                companyEmail = "companyEmail"
            )
            val brand = brandRepository.addNewBrand(signUpDto)

            val insertToken = InsertBrandToken(
                bId = brand.id,
                token = "token",
                scope = "scope",
                expiresIn = 1000
            )
            val expected = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope",
                expiresIn = 1000
            )
            brandTokenRepository.saveTokenForBrandId(insertToken)

            val actual = brandTokenRepository.getByBrandId(brand.id)
            Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }
}
