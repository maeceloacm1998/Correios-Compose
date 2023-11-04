package com.puc.correios.feature.details.domain

import com.puc.correios.core.utils.DateUtils
import com.puc.correios.feature.commons.database.events.database.EventsDAO
import com.puc.correios.feature.commons.database.events.database.EventsEntity
import com.puc.correios.feature.details.data.network.response.TrackingResponse

class AddTrackingInDatabaseUseCase(private val dao: EventsDAO) {
    suspend operator fun invoke(tracking: TrackingResponse) {
        if (dao.existEvent(tracking.code) == EMPTY_LIST) {
            dao.addEvents(
                EventsEntity(
                    id = System.currentTimeMillis().hashCode(),
                    cod = tracking.code,
                    lastDate = DateUtils.converterData(tracking.lastUpdated)
                )
            )
        }
    }

    companion object {
        const val EMPTY_LIST = 0
    }
}

