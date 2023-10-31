package com.puc.correios.feature.home.data

import com.puc.correios.feature.home.data.network.HomeService
import com.puc.correios.feature.home.data.network.response.ValidateTokenResponse
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(private val api: HomeService) : HomeRepository {
    override suspend fun fetchValidateToken(): Flow<ValidateTokenResponse> = api.fetchValidateToken()
}