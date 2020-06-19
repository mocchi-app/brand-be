package org.mocchi.brand.schedule

import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.springframework.stereotype.Service

@Service
class JobFactory {

    fun createJobForBrand(brandId: Long): JobDetail =
        JobBuilder.newJob()
            .ofType(BrandSyncJob::class.java)
            .usingJobData("brandId", brandId)
            .build()
}
