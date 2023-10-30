package com.puc.correios.feature.login.data.di

import com.puc.correios.feature.login.data.LoginRepository
import com.puc.correios.feature.login.data.LoginRepositoryImpl
import com.puc.correios.feature.login.data.network.LoginService
import com.puc.correios.feature.login.data.network.LoginServiceImpl
import com.puc.correios.feature.login.domain.GetTestUseCase
import com.puc.correios.feature.login.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LoginDependencyInjection {
    val loginModules = module {
        factory<LoginService> { LoginServiceImpl(get()) }
        factory<LoginRepository> { LoginRepositoryImpl(get()) }
        factory { GetTestUseCase(get()) }
        viewModel { LoginViewModel(get()) }
    }
}