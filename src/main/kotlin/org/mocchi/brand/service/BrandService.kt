package org.mocchi.brand.service

import org.mocchi.brand.model.entity.Brand
import org.mocchi.brand.model.entity.InsertBrand
import org.mocchi.brand.repository.BrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {
    suspend fun insertOrFindBrand(insertBrand: InsertBrand): Brand =
        brandRepository.getByUrl(insertBrand.url) ?: brandRepository.addNewBrand(insertBrand)

}
