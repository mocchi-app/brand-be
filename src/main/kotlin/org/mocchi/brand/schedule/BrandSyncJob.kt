package org.mocchi.brand.schedule

import kotlinx.coroutines.runBlocking
import org.mocchi.brand.repository.BrandRepository
import org.mocchi.brand.repository.BrandTokenRepository
import org.mocchi.brand.service.ShopifyService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class BrandSyncJob(
    private val brandRepository: BrandRepository,
    private val brandTokenRepository: BrandTokenRepository,
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
                                .also {
                                    log.info("Received products: ${it}")
                                }
                        }
                }
            }

    }
}
