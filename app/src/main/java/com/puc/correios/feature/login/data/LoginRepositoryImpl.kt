package com.puc.correios.feature.login.data

import com.puc.correios.feature.login.data.network.LoginService
import com.puc.correios.feature.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(private val api: LoginService) : LoginRepository {
    override suspend fun getTest(): Flow<LoginResponse> {
        return api.getTest()
    }
}