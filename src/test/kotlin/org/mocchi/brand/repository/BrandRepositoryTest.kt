package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.FullBrand
import org.mocchi.brand.model.entity.InsertBrand
import org.mocchi.brand.model.entity.InsertBrandToken
import org.mocchi.brand.model.entity.Token
import org.springframework.beans.factory.annotation.Autowired

internal class BrandRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var brandTokenRepository: BrandTokenRepository

    @Test
    fun `should insert new brand`() {
        runBlocking {
            val insertBrand = insertBrand()
            val expected = Brand(
                id = 1,
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val actual = brandRepository.addNewBrand(insertBrand)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }

    @Test
    fun `should fail for new brand when url exists`() {
        runBlocking {
            val insertBrand = insertBrand()
            val expected = Brand(
                id = 1,
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val actual = brandRepository.addNewBrand(insertBrand)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()

            assertThrows<RuntimeException> {
                runBlocking {
                    brandRepository.addNewBrand(insertBrand)
                }
            }
        }
    }

    @Test
    fun `should find existing brand`() {
        runBlocking {
            val signUpDto = insertBrand()
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

            val actual = brandRepository.getByUrl(signUpDto.url)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }

    @Test
    fun `should get brand joined with token when id exists`() {
        runBlocking {
            val insertBrand = insertBrand()
            val brand = brandRepository.addNewBrand(insertBrand)
            assertThat(brand).hasNoNullFieldsOrProperties()

            val token = brandTokenRepository.saveTokenForBrandId(
                InsertBrandToken(
                    brandId = brand.id,
                    token = "token",
                    scope = "scope"
                )
            )
            val actual = brandRepository.getByIdJoinWithToken(brand.id)
            val expected = FullBrand(
                brandId = brand.id,
                fullName = brand.fullName,
                url = brand.url,
                email = brand.email,
                token = Token(token.token, token.scope),
                payment = null
            )
            assertThat(actual).isEqualTo(expected)
        }
    }

    private fun insertBrand(): InsertBrand {
        return InsertBrand(
            fullName = "companyName",
            url = "companyUrl",
            email = "companyEmail"
        )
    }
}
