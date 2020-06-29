package org.mocchi.brand.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    companion object {
        const val DEFAULT_INCLUDE_PATTERN = "/api/.*"
    }

    @Bean
    fun documentation(): Docket = Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.mocchi.brand"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .securityContexts(listOf(securityContext()))
        .securitySchemes(listOf(apiKey()))
        .apiInfo(appMetadata())

    private fun apiKey(): ApiKey =
        ApiKey("JWT", "Authorization", "header")


    private fun securityContext(): SecurityContext =
        SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
            .build()

    fun defaultAuth(): List<SecurityReference?> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(
            SecurityReference("JWT", authorizationScopes)
        )
    }

    private fun appMetadata(): ApiInfo = ApiInfoBuilder()
        .title("Brand")
        .description("Brand onboarding service")
        .version("1.0")
        .build()
}
