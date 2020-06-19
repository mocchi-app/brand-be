package org.mocchi.brand.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SyncBrandProducts {

    @Scheduled(fixedDelay = 10_000)
    fun syncBrandProducts() {

    }
}
