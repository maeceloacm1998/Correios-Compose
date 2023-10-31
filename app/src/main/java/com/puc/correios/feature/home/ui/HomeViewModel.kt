package com.puc.correios.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puc.correios.core.utils.UiState
import com.puc.correios.feature.home.domain.GetValidateTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class HomeViewModel(private val getValidateTokenUseCase: GetValidateTokenUseCase) : ViewModel() {

    private val _token = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val token: StateFlow<UiState<Unit>> = _token

    init {
        fetchValidateToken()
    }

    private fun fetchValidateToken() {
        viewModelScope.launch {
            try {
                val response = getValidateTokenUseCase().single()
                val notExistToken = response.authToken == null
                if (notExistToken) {
                    _token.value = UiState.Error(Throwable())
                } else {
                    _token.value = UiState.Success(Unit)
                }
            } catch (e: Exception) {
                _token.value = UiState.Error(e)
            }
        }
    }

}