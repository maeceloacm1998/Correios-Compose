package com.puc.correios.feature.login.data.network

import com.puc.correios.feature.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class LoginServiceImpl(retrofit: Retrofit) : LoginService {

    private val client: LoginClient = retrofit.create(LoginClient::class.java)
    override suspend fun getTest(): Flow<LoginResponse> = flow {
        emit(client.getTest())
    }

}