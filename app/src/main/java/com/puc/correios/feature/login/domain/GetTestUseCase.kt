package com.puc.correios.feature.login.domain

import com.puc.correios.feature.login.data.LoginRepository
import com.puc.correios.feature.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.Flow

class GetTestUseCase(private val repository: LoginRepository) {

    suspend operator fun invoke(): Flow<LoginResponse> = repository.getTest()
}