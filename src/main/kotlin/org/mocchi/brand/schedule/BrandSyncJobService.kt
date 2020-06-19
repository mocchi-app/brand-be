package org.mocchi.brand.schedule

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
        scheduler.scheduleJob(
            jobFactory.createJobForBrand(brandId),
            TriggerBuilder.newTrigger()
                .withSchedule(
                    simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(5)
                )
                .build()
        )
    }
}
