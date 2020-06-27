package org.mocchi.brand.repository

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.BrandWithToken
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
                scope = "scope"
            )
            val expected = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope"
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
                scope = "scope"
            )
            val expected = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope"
            )
            val actual = brandTokenRepository.saveTokenForBrandId(insertToken)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()

            val updateToken = InsertBrandToken(
                brandId = brand.id,
                token = "token2",
                scope = "scope2"
            )
            val expectedUpdated = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token2",
                scope = "scope2"
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
                scope = "scope"
            )
            val expected = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope"
            )
            val actual = brandTokenRepository.saveTokenForBrandId(insertToken)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()

            val updateToken = InsertBrandToken(
                brandId = -1,
                token = "token2",
                scope = "scope2"
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
                scope = "scope"
            )
            val expected = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope"
            )
            brandTokenRepository.saveTokenForBrandId(insertToken)

            val actual = brandTokenRepository.getByBrandId(brand.id)
            assertThat(actual)
                .isEqualToIgnoringGivenFields(expected, "id")
                .hasNoNullFieldsOrProperties()
        }
    }

    @Test
    fun `should return null when brand doesn't exist`() {
        runBlocking {
            val actual = brandTokenRepository.getByBrandId(0)
            assertThat(actual).isNull()
        }
    }

    @Test
    fun `should find existing brand by token`() {
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
                scope = "scope"
            )
            val token = BrandToken(
                id = 1,
                bId = brand.id,
                token = "token",
                scope = "scope"
            )
            brandTokenRepository.saveTokenForBrandId(insertToken)

            val actual = brandTokenRepository.getBrandByToken(insertToken.token)
                .awaitFirst()
            val expected = BrandWithToken(
                brand.id, brand.fullName, brand.url, brand.email, token.token, token.scope
            )
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `should return empty mono when token doesn't exist`() {
        runBlocking {
            val actual = brandTokenRepository.getBrandByToken("doesn't exist")
            assertThat(actual.awaitFirstOrNull()).isNull()
        }
    }
}
