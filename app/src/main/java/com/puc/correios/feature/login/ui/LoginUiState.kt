package com.puc.correios.feature.login.ui

import com.puc.correios.feature.login.data.network.response.LoginResponse

sealed interface LoginUiState {
    object Loading: LoginUiState
    data class Error(val throwable: Throwable): LoginUiState
    data class Success(val response: LoginResponse): LoginUiState
}