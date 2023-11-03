package com.puc.correios.feature.details.data

import com.puc.correios.feature.details.data.network.DetailsService
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import kotlinx.coroutines.flow.Flow

class DetailsRepositoryImpl(private val api: DetailsService) : DetailsRepository {
    override suspend fun getTracking(code: String): Flow<TrackingResponse> = api.getTracking(code)
}