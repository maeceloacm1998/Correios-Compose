package com.puc.correios.feature.login.data.network

import com.puc.correios.feature.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginService {
    suspend fun getTest(): Flow<LoginResponse>
}