package org.mocchi.brand.schedule

import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Service

@Service
class BrandSyncJobService(
    private val scheduler: Scheduler,
    private val jobFactory: JobFactory
) {

    fun startSyncProcessForBrand(brandId: Long) {
        if (!scheduler.checkExists(JobKey.jobKey(brandId.toString()))) {
            scheduler.scheduleJob(
                jobFactory.createJobForBrand(brandId),
                TriggerBuilder.newTrigger()
                    .withSchedule(
                        simpleSchedule()
                            .repeatForever()
                            .withIntervalInSeconds(60)
                    )
                    .build()
            )
        }
    }
}
