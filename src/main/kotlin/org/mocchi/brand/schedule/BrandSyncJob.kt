package org.mocchi.brand.schedule

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.mocchi.brand.convert.ShopifyProductConverter
import org.mocchi.brand.repository.BrandProductRepository
import org.mocchi.brand.repository.BrandRepository
import org.mocchi.brand.service.ShopifyService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class BrandSyncJob(
    private val brandRepository: BrandRepository,
    private val brandProductRepository: BrandProductRepository,
    private val shopifyProductConverter: ShopifyProductConverter,
    private val shopifyService: ShopifyService
) : Job {
    private val log = LoggerFactory.getLogger(BrandSyncJob::class.java)

    override fun execute(context: JobExecutionContext) {
        context.jobDetail.jobDataMap["brandId"]
            .takeIf { it is Long }
            ?.let { it as Long }
            ?.let { brandId ->
                runBlocking {
                    brandRepository.getByIdJoinWithToken(brandId)
                        ?.let { brand ->
                            shopifyService.fetchAllProducts(brand.url, brand.token)
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
                }
            }
            ?: run {
                log.warn("Can't find brand in ${context.jobDetail.jobDataMap}")
            }

    }
}
