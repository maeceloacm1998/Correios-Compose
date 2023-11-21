package com.puc.correios.feature.commons.database.events.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EventsEntity")
data class EventsEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "cod")
    val cod: String,

    @ColumnInfo(name = "lastDate")
    val lastDate: String
)