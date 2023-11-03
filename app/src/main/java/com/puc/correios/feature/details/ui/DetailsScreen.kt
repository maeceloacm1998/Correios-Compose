package com.puc.correios.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.puc.correios.core.utils.UiState
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import com.puc.correios.feature.home.ui.model.HomeEventsModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    cod: String?,
    viewModel: DetailsViewModel = koinViewModel()
) {
    cod?.let { viewModel.fetchTracking(it) }

    Column(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxSize()
    ) {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        val uiState by produceState<UiState<TrackingResponse>>(
            initialValue = UiState.Loading, key1 = lifecycle, key2 = viewModel
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value = it }
            }
        }

        when(uiState) {
            is UiState.Error -> TODO()
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> {
                val response = (uiState as UiState.Success).response
            }
        }
    }
}