package org.mocchi.brand.schedule

import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Min

@Service
class BrandSyncJobService(
    private val scheduler: Scheduler,
    private val jobFactory: JobFactory,
    private val brandSyncProperties: BrandSyncProperties
) {

    fun startSyncProcessForBrand(brandId: Long) {
        if (!scheduler.checkExists(JobKey.jobKey(brandId.toString()))) {
            scheduler.scheduleJob(
                jobFactory.createJobForBrand(brandId),
                TriggerBuilder.newTrigger()
                    .withSchedule(
                        simpleSchedule()
                            .repeatForever()
                            .withIntervalInMinutes(brandSyncProperties.periodInMinutes)
                    )
                    .build()
            )
        }
    }
}

@Validated
@Component
@ConfigurationProperties(prefix = "brand.sync")
class BrandSyncProperties {

    @Min(1)
    var periodInMinutes: Int = 0
}
