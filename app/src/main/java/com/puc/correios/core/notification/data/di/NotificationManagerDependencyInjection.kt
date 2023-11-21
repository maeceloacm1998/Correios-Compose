package com.puc.correios.core.notification.data.di

import com.puc.correios.core.notification.NotificationCustomManager
import com.puc.correios.core.notification.NotificationCustomManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object NotificationManagerDependencyInjection {
    val modules = module {
        single<NotificationCustomManager> { NotificationCustomManagerImpl(androidContext(), get()) }
    }
}