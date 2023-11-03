package com.puc.correios.feature.details.data.network

import com.puc.correios.core.network.RetrofitDependencyInjection
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsServiceImpl : DetailsService {
    private val retrofit =
        RetrofitDependencyInjection.getRetrofit("https://api.linketrack.com/")
    private val client = retrofit.create(DetailsClient::class.java)
    override suspend fun getTracking(code: String): Flow<TrackingResponse> = flow {
        emit(client.getTracking(TOKEN, USER, code))
    }


    companion object {
        // Para conseguir o token e user, precisa entrar em contato com os devs da link e Track
        const val TOKEN = "5a3a75f908aefaab178309e757e7104e0d24149cc23acf692c80080f5c11c2ee"
        const val USER = "marcelochmendes@gmail.com"
    }
}