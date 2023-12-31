package com.puc.correios.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puc.correios.core.notification.NotificationCustomManager
import com.puc.correios.core.utils.UiState
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import com.puc.correios.feature.details.domain.AddTrackingInDatabaseUseCase
import com.puc.correios.feature.details.domain.GetTrackingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getTrackingUseCase: GetTrackingUseCase,
    private val addTrackingInDatabaseUseCase: AddTrackingInDatabaseUseCase,
    private val notificationCustomManager: NotificationCustomManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<TrackingResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<TrackingResponse>> = _uiState

    fun fetchTracking(code: String) {
        viewModelScope.launch {
            try {
                val response = getTrackingUseCase(code).single()
                addTrackingInDatabaseUseCase(response)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

    fun handleNotificationEnabled(checked: Boolean) {
        if (checked) {
            notificationCustomManager.openAppNotificationSettings()
        }
    }

    fun isNotificationEnabled(): Boolean = notificationCustomManager.isNotificationEnabled()
}