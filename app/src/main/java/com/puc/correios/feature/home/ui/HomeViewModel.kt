package com.puc.correios.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puc.correios.core.utils.UiState
import com.puc.correios.feature.home.domain.GetHomeEventsUseCase
import com.puc.correios.feature.home.ui.model.HomeEventsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(val getHomeEventsUseCase: GetHomeEventsUseCase) : ViewModel() {

    var uiState: StateFlow<UiState<List<HomeEventsModel>>> =
        getHomeEventsUseCase().map { UiState.Success(it) }.catch { UiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), UiState.Loading)

    private val _errorSearchCode = MutableLiveData<Boolean>()
    val errorSearchCode: LiveData<Boolean> = _errorSearchCode

    fun updateUiState() {
        viewModelScope.launch {
            val newState = getHomeEventsUseCase()
                .map { UiState.Success(it) }
                .catch { UiState.Error(it) }

            (uiState as? MutableStateFlow)?.let { mutableStateFlow ->
                mutableStateFlow.value = UiState.Loading
                newState.collect { value ->
                    mutableStateFlow.value = value
                }
            }
        }
    }

    fun validateSearchCode(searchCode: String, onNavigation: (searchCode: String) -> Unit) {
        if (isNotValidateSearchCode(searchCode)) {
            _errorSearchCode.value = true
        } else {
            onNavigation(searchCode)
        }
    }

    private fun isNotValidateSearchCode(searchCode: String): Boolean {
        val searchCodeRegex = Regex("[A-Za-z]{2}\\d{9}[A-Za-z]{2}")
        return !searchCodeRegex.matches(searchCode)
    }

    // Exemplo uso stateFlow com API
//    private val _token = MutableStateFlow<UiState<Unit>>(UiState.Loading)
//    val token: StateFlow<UiState<Unit>> = _token

    // Exemplo uso StateFlow com API
//    private fun fetchValidateToken() {
//        viewModelScope.launch {
//            try {
//                val response = getValidateTokenUseCase().single()
//                val notExistToken = response.authToken == null
//                if (notExistToken) {
//                    _token.value = UiState.Error(Throwable())
//                } else {
//                    _token.value = UiState.Success(Unit)
//                }
//            } catch (e: Exception) {
//                _token.value = UiState.Error(e)
//            }
//        }
//    }
}