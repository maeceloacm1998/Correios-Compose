package com.puc.correios.feature.commons.database.events.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDAO {
    @Query("SELECT * from EventsEntity")
    fun getEvents(): Flow<List<EventsEntity>>

    @Query("SELECT COUNT(*) FROM EventsEntity WHERE cod=:cod")
    suspend fun existEvent(cod: String): Int

    @Insert
    suspend fun addEvents(event: EventsEntity)
}