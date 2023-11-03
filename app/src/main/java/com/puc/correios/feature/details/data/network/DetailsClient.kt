package com.puc.correios.feature.details.data.network

import com.puc.correios.feature.details.data.network.response.TrackingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailsClient {
    @GET("track/json")
    suspend fun getTracking(
        @Query("token") token: String,
        @Query("user") user: String,
        @Query("codigo") cod: String
    ): TrackingResponse
}