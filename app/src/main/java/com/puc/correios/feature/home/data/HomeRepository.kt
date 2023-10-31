package com.puc.correios.feature.home.data

import com.puc.correios.feature.home.data.network.response.ValidateTokenResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun fetchValidateToken(): Flow<ValidateTokenResponse>
}