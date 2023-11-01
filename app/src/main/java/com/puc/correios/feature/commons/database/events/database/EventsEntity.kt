package com.puc.correios.feature.commons.database.events.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EventsEntity")
data class EventsEntity(
    @PrimaryKey
    val id: Int,
    val cod: String,
    val host: String,
    val events: List<Event>,
    val lastDate: String
)

data class Event(
    val date: String,
    val hour: String,
    val localization: String,
    val status: String,
)