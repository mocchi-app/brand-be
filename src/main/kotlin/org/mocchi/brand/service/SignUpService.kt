package org.mocchi.brand.service

import org.mocchi.brand.model.controller.SignUpDto
import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.repository.BrandRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SignUpService(
    private val brandRepository: BrandRepository
) {

    val random = Random()

    suspend fun signUpBrand(signUpDto: SignUpDto): Int =
        insertOrFindBrand(signUpDto)
            .let { random.nextInt() }

    private suspend fun insertOrFindBrand(signUpDto: SignUpDto): Brand =
        brandRepository.getByUrl(signUpDto.companyUrl) ?: brandRepository.addNewBrand(signUpDto)
}
