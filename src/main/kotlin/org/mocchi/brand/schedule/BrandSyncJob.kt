package org.mocchi.brand.schedule

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class BrandSyncJob : Job {
    private val log = LoggerFactory.getLogger(BrandSyncJob::class.java)

    override fun execute(context: JobExecutionContext) {
        log.info("Executing job for brand: ${context.jobDetail.jobDataMap["brandId"]}")
    }
}
