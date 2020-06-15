package org.mocchi.brand.configuration

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.DATABASE
import io.r2dbc.spi.ConnectionFactoryOptions.DRIVER
import io.r2dbc.spi.ConnectionFactoryOptions.HOST
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.PORT
import io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Configuration
class PostgresConfiguration {

    @Bean
    fun postgresqlConnectionFactory(databaseProperty: DatabaseProperties): ConnectionFactory = ConnectionFactories.get(
        ConnectionFactoryOptions.builder()
            .option(DRIVER, "pool")
            .option(PROTOCOL, "postgresql")
            .option(HOST, databaseProperty.host)
            .option(PORT, databaseProperty.port)
            .option(USER, databaseProperty.username)
            .option(PASSWORD, databaseProperty.password)
            .option(DATABASE, databaseProperty.database)
            .build()
    )

    @Bean
    fun databaseClient(postgresqlConnectionFactory: ConnectionFactory): DatabaseClient =
        DatabaseClient.create(postgresqlConnectionFactory)
}

@Validated
@Component
@ConfigurationProperties(prefix = "db")
class DatabaseProperties {

    @NotBlank
    lateinit var host: String

    @NotBlank
    lateinit var database: String

    var port: Int = 0

    @NotBlank
    lateinit var username: String

    @NotBlank
    lateinit var password: String
}
