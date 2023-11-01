package com.puc.correios.feature.commons.database.events.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EventsEntity::class], version = 1)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDAO
}