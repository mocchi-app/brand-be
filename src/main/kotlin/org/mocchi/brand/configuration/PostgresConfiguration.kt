package org.mocchi.brand.configuration

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.net.URI
import javax.validation.constraints.NotBlank

@Configuration
class PostgresConfiguration {

    @Bean
    fun postgresqlConnectionFactory(databaseProperty: DatabaseProperties): ConnectionFactory =
        URI(databaseProperty.url).let {
            ConnectionFactories.get(
                builder()
                    .option(DRIVER, "pool")
                    .option(PROTOCOL, "postgresql")
                    .option(HOST, it.host)
                    .option(PORT, it.port)
                    .option(USER, databaseProperty.username)
                    .option(PASSWORD, databaseProperty.password)
                    .option(DATABASE, it.path.replace("/", ""))
                    .build()
            )
        }

    @Bean
    fun databaseClient(postgresqlConnectionFactory: ConnectionFactory): DatabaseClient =
        DatabaseClient.create(postgresqlConnectionFactory)
}

@Validated
@Component
@ConfigurationProperties(prefix = "db")
class DatabaseProperties {

    @NotBlank
    lateinit var url: String

    @NotBlank
    lateinit var username: String

    @NotBlank
    lateinit var password: String
}
