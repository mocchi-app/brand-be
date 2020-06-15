package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitRowsUpdated

internal class BrandRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @BeforeEach
    fun setUp() {
        runBlocking {
            databaseClient.delete()
                .from(Brand::class.java)
                .fetch()
                .awaitRowsUpdated()
        }
    }

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

    @Test
    fun `should fail for new brand when url exists`() {
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

            assertThrows<RuntimeException> {
                runBlocking {
                    brandRepository.addNewBrand(signUpDto)
                }
            }
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
            val expected = Brand(
                id = 1,
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val check = brandRepository.addNewBrand(signUpDto)
            assertThat(check)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()

            val actual = brandRepository.getByUrl(signUpDto.companyUrl)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }
}
