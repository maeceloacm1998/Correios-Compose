package com.puc.correios.feature.login.data

import kotlinx.coroutines.flow.Flow
import com.puc.correios.feature.login.data.network.response.LoginResponse

interface LoginRepository {
    suspend fun getTest(): Flow<LoginResponse>
}