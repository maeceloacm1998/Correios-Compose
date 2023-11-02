package com.puc.correios.feature.home.data

import com.puc.correios.feature.commons.database.events.database.EventsDAO
import com.puc.correios.feature.home.ui.model.HomeEventsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(eventsDAO: EventsDAO) : HomeRepository {
    override val tasks: Flow<List<HomeEventsModel>> = eventsDAO.getEvents().map { items ->
        items.map {
            HomeEventsModel(
                id = System.currentTimeMillis().hashCode(),
                cod = it.cod,
                lastDate = it.lastDate
            )
        }
    }
}