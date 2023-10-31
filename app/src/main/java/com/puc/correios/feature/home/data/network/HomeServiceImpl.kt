package com.puc.correios.feature.home.data.network

import com.puc.correios.core.network.RetrofitDependencyInjection
import com.puc.correios.feature.home.data.network.response.ValidateTokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class HomeServiceImpl : HomeService {
    private val retrofit: Retrofit =
        RetrofitDependencyInjection.getRetrofit("https://api.correios.com.br/token/v1/")
    private val client = retrofit.create(HomeClient::class.java)

    override suspend fun fetchValidateToken(): Flow<ValidateTokenResponse> = flow {
        emit(client.fetchValidateToken())
    }
}