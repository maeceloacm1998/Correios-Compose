package com.puc.correios.feature.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puc.correios.feature.login.domain.GetTestUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class LoginViewModel(private val getTestUseCase: GetTestUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        performLogin()
    }

    private fun performLogin() {
        viewModelScope.launch {
            try {
                val response = getTestUseCase().single()
                if (response.success != 0) {
                    _uiState.value = LoginUiState.Success(response)
                } else {
                    _uiState.value = LoginUiState.Error(Throwable("Login failed")) // Ou uma mensagem de erro apropriada
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e)
            }
        }
    }
}