package org.mocchi.brand.configuration

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration {

    @Bean
    fun dataSource(databaseProperties: DatabaseProperties): PGSimpleDataSource =
        DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .type(PGSimpleDataSource::class.java)
            .url("jdbc:postgresql://${databaseProperties.host}:${databaseProperties.port}/${databaseProperties.database}")
            .username(databaseProperties.username)
            .password(databaseProperties.password)
            .build()

}
