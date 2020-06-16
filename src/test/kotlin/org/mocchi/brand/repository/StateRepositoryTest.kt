package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.StateCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitRowsUpdated

internal class StateRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var stateCodeRepository: StateCodeRepository

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @Test
    fun `should store code for a brand and retrieve it by id`() {
        runBlocking {
            val brand = brandRepository.addNewBrand(SignUpDto("company", "url", "email"))

            val code = stateCodeRepository.saveNewCode(brand.id)
            assertThat(code).isNotNull
            assertThat(code.id).isNotNull()
            assertThat(code.brandId).isEqualTo(brand.id)

            val actual = stateCodeRepository.getById(code.id)
            assertThat(actual).isEqualTo(code)
        }
    }
}
