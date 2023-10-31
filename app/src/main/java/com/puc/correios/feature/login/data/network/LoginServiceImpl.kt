package com.puc.correios.feature.login.data.network

import com.puc.correios.core.network.RetrofitDependencyInjection
import com.puc.correios.feature.login.data.network.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class LoginServiceImpl : LoginService {
    private val retrofit: Retrofit =
        RetrofitDependencyInjection.getRetrofit("https://api.correios.com.br/token/v1/")
    private val client: LoginClient = retrofit.create(LoginClient::class.java)

    override suspend fun handleLogin(): Flow<LoginResponse> = flow {
        emit(client.handleLogin())
    }
}