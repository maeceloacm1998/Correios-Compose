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
        const val TOKEN = ""
        const val USER = ""
    }
}