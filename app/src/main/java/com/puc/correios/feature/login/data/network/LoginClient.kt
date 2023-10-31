package com.puc.correios.feature.login.data.network

import com.puc.correios.feature.login.data.network.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginClient {
    @POST("autentica")
    suspend fun handleLogin(

    ): LoginResponse
}