package com.puc.correios.feature.home.data.network

import com.puc.correios.feature.home.data.network.response.ValidateTokenResponse
import retrofit2.http.POST

interface HomeClient {
    @POST("autentica")
    suspend fun fetchValidateToken(): ValidateTokenResponse
}