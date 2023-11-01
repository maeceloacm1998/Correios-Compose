package com.puc.correios.feature.commons.database.events.di

import android.content.Context
import androidx.room.Room
import com.puc.correios.feature.commons.database.events.database.EventsDAO
import com.puc.correios.feature.commons.database.events.database.EventsDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object EventsDatabaseDependencyInjection {
    private const val DATABASE_NAME = "EventsDatabase"

    val eventsModules = module {
        single { provideEventsDatabase(androidApplication()) }
        single { provideEventsDao(get()) }
    }

    private fun provideEventsDao(eventsDatabase: EventsDatabase): EventsDAO {
        return eventsDatabase.eventsDao()
    }

    private fun provideEventsDatabase(@ApplicationContext applicationContext: Context): EventsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            EventsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}