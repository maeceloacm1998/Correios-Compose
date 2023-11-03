package com.puc.correios.feature.details.data

import com.puc.correios.feature.details.data.network.response.TrackingResponse
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getTracking(code: String): Flow<TrackingResponse>
}