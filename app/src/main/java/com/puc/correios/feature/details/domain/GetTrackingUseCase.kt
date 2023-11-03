package com.puc.correios.feature.details.domain

import com.puc.correios.feature.details.data.DetailsRepository
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import kotlinx.coroutines.flow.Flow

class GetTrackingUseCase(private val repository: DetailsRepository) {
    suspend operator fun invoke(code: String): Flow<TrackingResponse> =
        repository.getTracking(code)
}