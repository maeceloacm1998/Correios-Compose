package com.puc.correios.feature.details.data.network

import com.puc.correios.feature.details.data.network.response.TrackingResponse
import kotlinx.coroutines.flow.Flow

interface DetailsService {
    suspend fun getTracking(code: String): Flow<TrackingResponse>
}