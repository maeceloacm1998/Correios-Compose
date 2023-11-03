package com.puc.correios.feature.home.data.di

import com.puc.correios.feature.home.data.HomeRepository
import com.puc.correios.feature.home.data.HomeRepositoryImpl
import com.puc.correios.feature.home.domain.GetHomeEventsUseCase
import com.puc.correios.feature.home.ui.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeDependencyInjection {
    val homeModules = module {
        factory<HomeRepository> { HomeRepositoryImpl(get()) }
        factory { GetHomeEventsUseCase(get()) }
        viewModel { HomeViewModel(androidContext(), get()) }
    }
}