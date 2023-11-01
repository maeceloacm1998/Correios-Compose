package com.puc.correios.feature.home.domain

import com.puc.correios.feature.home.data.HomeRepository
import com.puc.correios.feature.home.ui.model.HomeEventsModel
import kotlinx.coroutines.flow.Flow

class GetHomeEventsUseCase(private val repository: HomeRepository) {
    operator fun invoke(): Flow<List<HomeEventsModel>> = repository.tasks
}