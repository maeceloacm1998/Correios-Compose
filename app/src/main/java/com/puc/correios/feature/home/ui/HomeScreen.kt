package com.puc.correios.feature.home.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.puc.correios.R
import com.puc.correios.components.textfield.TextFieldCustom
import com.puc.correios.core.utils.UiState
import com.puc.correios.ui.theme.Surface
import com.puc.correios.ui.theme.SurfaceLight
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<UiState<Unit>>(
        initialValue = UiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.token.collect { value = it }
        }
    }

    when (uiState) {
        is UiState.Error -> TODO()
        UiState.Loading -> TODO()
        is UiState.Success -> Screen()
    }
}

@Preview
@Composable
fun Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
    ) {
        Header()
    }
}

@Preview
@Composable
fun Header() {
    var search by rememberSaveable { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SurfaceLight,
                shape = RoundedCornerShape(bottomStart = 50f, bottomEnd = 50f)
            )
            .padding(horizontal = 24.dp, vertical = 30.dp)
    ) {
        val (tfSearch) = createRefs()

        TextFieldCustom(modifier = Modifier
            .constrainAs(tfSearch) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            onChangeListener = { search = it },
            placeholder = stringResource(R.string.home_text_field_search_placeholder),
            label = stringResource(R.string.home_text_field_search_label),
            endIconImageVector = Icons.Filled.Search,
            endIconDescription = stringResource(R.string.home_text_field_search_icon_description),
            endIconListener = { Log.d("opa", "clicou") })
    }
}