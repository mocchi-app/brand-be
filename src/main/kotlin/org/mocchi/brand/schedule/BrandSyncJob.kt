package org.mocchi.brand.schedule

import kotlinx.coroutines.runBlocking
import org.mocchi.brand.repository.BrandRepository
import org.mocchi.brand.service.BrandSyncService
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class BrandSyncJob(
        private val brandRepository: BrandRepository,
        private val brandSyncService: BrandSyncService
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
                                    brandSyncService.syncBrandWithId(brandId, brand)
                                }
                    }
                }
                ?: run {
                    log.warn("Can't find brand in ${context.jobDetail.jobDataMap}")
                }

    }
}
