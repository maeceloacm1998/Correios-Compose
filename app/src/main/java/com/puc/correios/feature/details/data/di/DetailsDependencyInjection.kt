package com.puc.correios.feature.details.data.di

import com.puc.correios.feature.details.data.DetailsRepository
import com.puc.correios.feature.details.data.DetailsRepositoryImpl
import com.puc.correios.feature.details.data.network.DetailsService
import com.puc.correios.feature.details.data.network.DetailsServiceImpl
import com.puc.correios.feature.details.domain.AddTrackingInDatabaseUseCase
import com.puc.correios.feature.details.domain.GetTrackingUseCase
import com.puc.correios.feature.details.ui.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DetailsDependencyInjection {
    val detailsModules = module {
        factory<DetailsService> { DetailsServiceImpl() }
        factory<DetailsRepository> { DetailsRepositoryImpl(get()) }
        factory { GetTrackingUseCase(get()) }
        factory { AddTrackingInDatabaseUseCase(get()) }
        viewModel { DetailsViewModel(get(), get()) }
    }
}