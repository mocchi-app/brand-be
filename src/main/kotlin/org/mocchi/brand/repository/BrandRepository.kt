package org.mocchi.brand.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.InsertBrand
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.stereotype.Repository

@Repository
open class BrandRepository(
    private val databaseClient: DatabaseClient,
    private val objectMapper: ObjectMapper
) {

    suspend fun addNewBrand(signUpDto: SignUpDto): Brand =
        databaseClient.insert()
            .into(InsertBrand::class.java)
            .using(
                InsertBrand(
                    fullName = signUpDto.companyName,
                    url = signUpDto.companyUrl,
                    email = signUpDto.companyEmail
                )
            )
            .fetch()
            .awaitFirstOrNull()
            .let {
                objectMapper.convertValue(it, Brand::class.java)
            }

}
