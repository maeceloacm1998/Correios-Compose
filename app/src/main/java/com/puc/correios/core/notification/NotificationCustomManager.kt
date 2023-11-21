package com.puc.correios.core.notification

import com.puc.correios.feature.commons.database.events.database.EventsEntity
import kotlinx.coroutines.flow.Flow

interface NotificationCustomManager {
    fun createChannel(idChannel: String, name: String)
    fun builderNotification(
        idChannel: String,
        title: String,
        deepLink: String,
        description: String,
        drawable: Int,
        idNotification: Int
    )

    fun openAppNotificationSettings()
    fun isNotificationEnabled(): Boolean
    suspend fun getTracking(): Flow<List<EventsEntity>>
}