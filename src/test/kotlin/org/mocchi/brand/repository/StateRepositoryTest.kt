package org.mocchi.brand.repository

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mocchi.brand.AbstractIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient

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
            val url = "url"
            val code = stateCodeRepository.saveNewCode(url)
            assertThat(code).isNotNull
            assertThat(code.id).isNotNull()
            assertThat(code.url).isEqualTo(url)

            val actual = stateCodeRepository.getById(code.id)
            assertThat(actual).isEqualTo(code)
        }
    }
}
