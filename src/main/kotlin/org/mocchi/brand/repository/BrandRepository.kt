package org.mocchi.brand.repository

import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.InsertBrand
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.stereotype.Repository

@Repository
open class BrandRepository(
    private val databaseClient: DatabaseClient
) {

    suspend fun addNewBrand(signUpDto: SignUpDto) =
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

}
