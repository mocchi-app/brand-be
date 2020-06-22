package org.mocchi.brand.configuration

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class FlywayConfiguration {

    @Bean
    fun dataSource(databaseProperties: DatabaseProperties): PGSimpleDataSource =
        URI(databaseProperties.url)
            .let {
                DataSourceBuilder.create()
                    .driverClassName("org.postgresql.Driver")
                    .type(PGSimpleDataSource::class.java)
                    .url("jdbc:postgresql://${it.host}:${it.port}/${it.path.replace("/", "")}")
                    .username(databaseProperties.username)
                    .password(databaseProperties.password)
                    .build()
            }
}
