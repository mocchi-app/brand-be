package org.mocchi.brand.repository

import io.r2dbc.postgresql.codec.Json
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.entity.InsertBrand
import org.mocchi.brand.model.entity.InsertBrandProduct
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.time.OffsetDateTime

internal class BrandProductRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var brandProductRepository: BrandProductRepository


    @Test
    fun `should insert several brand products`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    "fullName",
                    "url",
                    "email"
                )
            )

            val firstBrand = generateBrandProduct(brand.id, 1)
            val secondBrand = generateBrandProduct(brand.id, 2)
            val actual = brandProductRepository.insertBrandProduct(
                listOf(firstBrand, secondBrand)
            ).asFlux().collectList().awaitFirst()
            assertThat(actual)
                .hasSize(2)
            assertThat(actual[0])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(firstBrand, "id", "variants")
            assertThat(actual[1])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(secondBrand, "id", "variants")
        }
    }

    @Test
    fun `should update one of brands on the second update`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    "fullName",
                    "url",
                    "email"
                )
            )

            val firstBrand = generateBrandProduct(brand.id, 1)
            val secondBrand = generateBrandProduct(brand.id, 2)
            val actual = brandProductRepository.insertBrandProduct(
                listOf(firstBrand, secondBrand)
            ).asFlux().collectList().awaitFirst()
            assertThat(actual)
                .hasSize(2)
            assertThat(actual[0])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(firstBrand, "id", "variants")
            assertThat(actual[1])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(secondBrand, "id", "variants")

            val updatedBrand = secondBrand.copy(title = "new title")

            val updatedActual = brandProductRepository.insertBrandProduct(
                listOf(firstBrand, updatedBrand)
            ).asFlux().collectList().awaitFirst()

            assertThat(updatedActual)
                .hasSize(2)
            assertThat(updatedActual[0])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(firstBrand, "id", "variants")
            assertThat(updatedActual[1])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(updatedBrand, "id", "variants")
        }
    }

    @Test
    fun `insert nulls`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    "fullName",
                    "url",
                    "email"
                )
            )

            val brandProduct = InsertBrandProduct(
                brandId = brand.id,
                shopifyId = 1
            )
            val actual = brandProductRepository.insertBrandProduct(
                listOf(brandProduct)
            ).asFlux().collectList().awaitFirst()

            assertThat(actual).hasSize(1)
            assertThat(actual[0]).isEqualToIgnoringGivenFields(brandProduct, "id")
        }
    }

    @Test
    fun `should select all products`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    "fullName",
                    "url",
                    "email"
                )
            )

            val firstBrand = generateBrandProduct(brand.id, 1)
            val secondBrand = generateBrandProduct(brand.id, 2)
            brandProductRepository.insertBrandProduct(
                listOf(firstBrand, secondBrand)
            ).asFlux().collectList().awaitFirst()

            val actual = brandProductRepository.selectAll()
                .asFlux().collectList().awaitFirst()

            assertThat(actual)
                .hasSize(2)
            assertThat(actual[0])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(firstBrand, "id", "variants")
            assertThat(actual[1])
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(secondBrand, "id", "variants")
        }
    }

    private fun generateBrandProduct(brandId: Long, shopifyId: Long) = InsertBrandProduct(
        brandId = brandId,
        shopifyId = shopifyId,
        title = RandomStringUtils.randomAlphabetic(10),
        bodyHtml = RandomStringUtils.randomAlphabetic(10),
        vendor = RandomStringUtils.randomAlphabetic(10),
        productType = RandomStringUtils.randomAlphabetic(10),
        createdAt = OffsetDateTime.now(),
        handle = RandomStringUtils.randomAlphabetic(10),
        updatedAt = OffsetDateTime.now(),
        publishedAt = OffsetDateTime.now(),
        templateSuffix = RandomStringUtils.randomAlphabetic(10),
        publishedScope = RandomStringUtils.randomAlphabetic(10),
        tags = RandomStringUtils.randomAlphabetic(10),
        variants = Json.of("{}"),
        imageSrc = RandomStringUtils.randomAlphabetic(10)
    )
}
