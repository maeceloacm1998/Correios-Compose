package com.puc.correios.feature.home.data

import com.puc.correios.feature.home.ui.model.HomeEventsModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val tasks: Flow<List<HomeEventsModel>>
}