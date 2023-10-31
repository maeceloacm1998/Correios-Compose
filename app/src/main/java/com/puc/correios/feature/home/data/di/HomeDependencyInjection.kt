package com.puc.correios.feature.home.data.di

import com.puc.correios.feature.home.data.HomeRepository
import com.puc.correios.feature.home.data.HomeRepositoryImpl
import com.puc.correios.feature.home.data.network.HomeService
import com.puc.correios.feature.home.data.network.HomeServiceImpl
import com.puc.correios.feature.home.ui.HomeViewModel
import com.puc.correios.feature.login.domain.GetTestUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeDependencyInjection {
    val homeModules = module {
        factory<HomeService> { HomeServiceImpl() }
        factory<HomeRepository> { HomeRepositoryImpl(get()) }
        factory { GetTestUseCase(get()) }
        viewModel { HomeViewModel(get()) }
    }
}