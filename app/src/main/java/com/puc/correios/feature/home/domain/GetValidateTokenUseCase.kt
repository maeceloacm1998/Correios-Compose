package com.puc.correios.feature.home.domain

import com.puc.correios.feature.home.data.HomeRepository
import com.puc.correios.feature.home.data.network.response.ValidateTokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetValidateTokenUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(): Flow<ValidateTokenResponse> {
        return repository.fetchValidateToken()
            .map { apiResponse ->
                val existToken = apiResponse.authToken != null

                if (existToken) {
                    // Salve os dados no Room
                }
                apiResponse
            }
    }
}