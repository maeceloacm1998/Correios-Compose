package com.puc.correios.feature.home.ui.model

import com.puc.correios.feature.commons.database.events.database.Event

data class HomeEventsModel(
    val id: Int,
    val cod: String,
    val host: String,
    val events: List<Event>,
    val lastDate: String
)