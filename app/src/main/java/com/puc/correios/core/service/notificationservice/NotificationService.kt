package com.puc.correios.core.service.notificationservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.puc.correios.R
import com.puc.correios.core.notification.NotificationCustomManager
import com.puc.correios.feature.commons.database.events.database.EventsEntity
import com.puc.correios.feature.details.data.DetailsRepository
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationService : Service(), KoinComponent {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val notificationCustomManager: NotificationCustomManager by inject()
    private val trackerRepository: DetailsRepository by inject()
    private val delayToFourHours: Long = 60 * 1000
    private var isServiceRunning = false

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        notificationCustomManager.createChannel(ID_CHANNEL, NAME_CHANNEL)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(1, notification)

        coroutineScope.launch {
            while (true) {
                handleNotification()
                isServiceRunning = true
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private suspend fun handleNotification() {
        notificationCustomManager.getTracking().collect {
            it.forEachIndexed { index, tracker ->
                fetchTrackerInNetworkService(
                    tracker = tracker, index = index
                )
            }
        }
    }

    private suspend fun fetchTrackerInNetworkService(tracker: EventsEntity, index: Int) {
        trackerRepository.getTracking(tracker.cod).runCatching {
            val response = single()

            // TODO logica para items que realmente estao em transito

//            if (changeQuantityEvents(trackerDb = tracker, trackerApi = response)) {
//                val lastUpdate = response.events.first()
//                createNotification(
//                    title = "Seu objeto teve atualizações",
//                    description = "Objeto encaminhado para ${lastUpdate.location}, com estado de ${lastUpdate.status}",
//                    idNotification = index
//                )
//            }

            val lastUpdate = response.events.first()
            createNotification(
                title = lastUpdate.status,
                description = "Objeto ${tracker.cod} encaminhado para ${lastUpdate.location}",
                idNotification = index
            )
        }
    }

    private fun changeQuantityEvents(
        trackerApi: TrackingResponse,
        trackerDb: EventsEntity
    ): Boolean {
        return trackerDb.events.size != trackerApi.events.size
    }

    private fun createNotification(
        title: String,
        description: String,
        idNotification: Int
    ) {

        notificationCustomManager.builderNotification(
            idChannel = ID_CHANNEL,
            title = title,
            deepLink = "",
            description = description,
            drawable = R.mipmap.ic_launcher,
            idNotification = idNotification
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ID_CHANNEL_BACKGROUND,
                "Canal do Serviço em Primeiro Plano",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, ID_CHANNEL_BACKGROUND)
            .setContentTitle("Serviço de notificação")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
    }

    companion object {
        const val ID_CHANNEL = "notification_channel"
        const val ID_CHANNEL_BACKGROUND = "notification_channel_bg"
        const val NAME_CHANNEL = "notification_name"
    }
}