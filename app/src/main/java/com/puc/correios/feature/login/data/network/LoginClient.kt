package com.puc.correios.feature.login.data.network

import com.puc.correios.feature.login.data.network.response.LoginResponse
import retrofit2.http.GET

interface LoginClient {
    @GET("f992f009-34ff-4932-a301-c66e90fa9b5c")
    suspend fun getTest(): LoginResponse
}