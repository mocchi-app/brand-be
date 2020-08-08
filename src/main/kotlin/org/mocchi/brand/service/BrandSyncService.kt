package org.mocchi.brand.service

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import org.mocchi.brand.convert.ShopifyProductConverter
import org.mocchi.brand.model.entity.FullBrand
import org.mocchi.brand.repository.BrandProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BrandSyncService(
        private val brandProductRepository: BrandProductRepository,
        private val shopifyProductConverter: ShopifyProductConverter,
        private val shopifyService: ShopifyService
) {

    private val log = LoggerFactory.getLogger(BrandSyncService::class.java)

    suspend fun syncBrandWithId(brandId: Long, brand: FullBrand) =
            shopifyService.fetchAllProducts(brand.url, brand.token.token)
                    .map { products ->
                        products.map {
                            shopifyProductConverter.convert(brandId, it)
                        }
                    }
                    .collect { products ->
                        brandProductRepository.insertBrandProduct(products)
                                .also {
                                    log.info("During sync for brand = $brandId ${it.count()} were synced")
                                }
                    }


}