package com.puc.correios.feature.commons.database.events.database

import androidx.room.TypeConverter

class EventsEntityTypeConverter {
    @TypeConverter
    fun fromListString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun toListString(value: List<String>): String {
        return value.joinToString(",")
    }
}