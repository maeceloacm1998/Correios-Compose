package com.puc.correios.feature.home.data.network

import com.puc.correios.feature.home.data.network.response.ValidateTokenResponse
import kotlinx.coroutines.flow.Flow

interface HomeService {
    suspend fun fetchValidateToken(): Flow<ValidateTokenResponse>
}