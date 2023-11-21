package com.puc.correios.feature.commons.database.events.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EventsEntity::class], version = 2)
@TypeConverters(EventsEntityTypeConverter::class)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDAO
}