package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.entity.InsertBrand
import org.mocchi.brand.model.entity.InsertBrandPayment
import org.springframework.beans.factory.annotation.Autowired

internal class BrandPaymentRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var brandPaymentRepository: BrandPaymentRepository

    @Test
    fun `should add new payment for a brand`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    fullName = "fullName",
                    url = "url",
                    email = "email"
                )
            )
            val insertBrandPayment = InsertBrandPayment(
                brandId = brand.id,
                customerId = "customerId",
                clientSecret = "clientSecret",
                commission = 10
            )
            val actual = brandPaymentRepository.insertNewPayment(
                insertBrandPayment
            )
            assertThat(actual)
                .isEqualToIgnoringGivenFields(insertBrandPayment, "id")
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -10, 100, 101])
    fun `should not add new payment for a brand as check failed`(commission: Int) {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    fullName = "fullName",
                    url = "url",
                    email = "email"
                )
            )
            val insertBrandPayment = InsertBrandPayment(
                brandId = brand.id,
                customerId = "customerId",
                clientSecret = "clientSecret",
                commission = commission
            )
            assertThrows<RuntimeException> {
                runBlocking {
                    brandPaymentRepository.insertNewPayment(
                        insertBrandPayment
                    )
                }
            }
        }
    }

    @Test
    fun `should not add the second new payment for a brand`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    fullName = "fullName",
                    url = "url",
                    email = "email"
                )
            )
            val insertBrandPayment = InsertBrandPayment(
                brandId = brand.id,
                customerId = "customerId",
                clientSecret = "clientSecret",
                commission = 10
            )
            val actual = brandPaymentRepository.insertNewPayment(
                insertBrandPayment
            )
            assertThat(actual)
                .isEqualToIgnoringGivenFields(insertBrandPayment, "id")
            assertThrows<RuntimeException> {
                runBlocking {
                    brandPaymentRepository.insertNewPayment(
                        insertBrandPayment.copy(customerId = "customerId2", clientSecret = "clientSecret2")
                    )
                }
            }
        }
    }

    @Test
    fun `should find a payment for a brand`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(
                InsertBrand(
                    fullName = "fullName",
                    url = "url",
                    email = "email"
                )
            )
            val insertBrandPayment = InsertBrandPayment(
                brandId = brand.id,
                customerId = "customerId",
                clientSecret = "clientSecret",
                commission = 10
            )
            brandPaymentRepository.insertNewPayment(
                insertBrandPayment
            )
            val actual = brandPaymentRepository.findByBrandId(brand.id)
            assertThat(actual)
                .isNotNull
                .isEqualToIgnoringGivenFields(insertBrandPayment, "id")

        }
    }

    @Test
    fun `should not find a payment for a brand as doesn't exist`() {
        runBlocking {
            val actual = brandPaymentRepository.findByBrandId(0)
            assertThat(actual)
                .isNull()
        }
    }
}
