package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.InsertBrand
import org.mocchi.brand.model.entity.InsertBrandToken
import org.springframework.beans.factory.annotation.Autowired

internal class BrandTokenRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var brandTokenRepository: BrandTokenRepository

    @Test
    fun `should insert new brand`() {
        runBlocking {
            val insertBrand = InsertBrand(
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val brand = brandRepository.addNewBrand(insertBrand)

            val insertToken = InsertBrandToken(
                brandId = brand.id,
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
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }

    @Test
    fun `should update token`() {
        runBlocking {
            val insertBrand = InsertBrand(
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val brand = brandRepository.addNewBrand(insertBrand)

            val insertToken = InsertBrandToken(
                brandId = brand.id,
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
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()

            val updateToken = InsertBrandToken(
                brandId = brand.id,
                token = "token2",
                scope = "scope2",
                expiresIn = 1001
            )
            val expectedUpdated = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token2",
                scope = "scope2",
                expiresIn = 1001
            )
            assertThat(brandTokenRepository.updateTokeForBrand(updateToken))
                .isEqualTo(1)

            assertThat(brandTokenRepository.getByBrandId(brand.id))
                .isEqualToIgnoringGivenFields(expectedUpdated, "id")

        }
    }

    @Test
    fun `should not update token when brand id is incorrect`() {
        runBlocking {
            val insertBrand = InsertBrand(
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val brand = brandRepository.addNewBrand(insertBrand)

            val insertToken = InsertBrandToken(
                brandId = brand.id,
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
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()

            val updateToken = InsertBrandToken(
                brandId = -1,
                token = "token2",
                scope = "scope2",
                expiresIn = 1001
            )
            assertThat(brandTokenRepository.updateTokeForBrand(updateToken))
                .isEqualTo(0)

            assertThat(brandTokenRepository.getByBrandId(brand.id))
                .isEqualToIgnoringGivenFields(expected, "id")

        }
    }


    @Test
    fun `should find existing brand`() {
        runBlocking {
            val insertBrand = InsertBrand(
                fullName = "companyName",
                url = "companyUrl",
                email = "companyEmail"
            )
            val brand = brandRepository.addNewBrand(insertBrand)

            val insertToken = InsertBrandToken(
                brandId = brand.id,
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
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }
}
