package com.puc.correios.core.utils

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
    data class Success<T>(val response: T) : UiState<T>()
}