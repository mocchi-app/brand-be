package org.mocchi.brand

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.BrandToken
import org.mocchi.brand.model.entity.StateCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitRowsUpdated
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File

@ExtendWith(value = [SpringExtension::class])
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTest {

    @LocalServerPort
    protected var serverPort: Int = 0

    companion object {
        private val dockerContainers = KDockerComposeContainer(File("src/test/resources/docker-compose-test.yml"))
            .withLocalCompose(true)
            .withExposedService("postgres", 5432, Wait.forListeningPort())

        init {
            dockerContainers.start()

            System.setProperty("db.port", dockerContainers.getServicePort("postgres", 5432).toString())
        }
    }

    @Autowired
    private lateinit var databaseClient: DatabaseClient

    @BeforeEach
    fun setUp() {
        runBlocking {
            databaseClient.delete()
                .from(BrandToken::class.java)
                .fetch()
                .awaitRowsUpdated()

            databaseClient.delete()
                .from(StateCode::class.java)
                .fetch()
                .awaitRowsUpdated()

            databaseClient.delete()
                .from(Brand::class.java)
                .fetch()
                .awaitRowsUpdated()
        }
    }
}

class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file)
