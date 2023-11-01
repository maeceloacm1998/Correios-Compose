package com.puc.correios.feature.home.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.puc.correios.R
import com.puc.correios.components.textfield.TextFieldCustom
import com.puc.correios.core.utils.UiState
import com.puc.correios.ui.theme.CustomDimensions
import com.puc.correios.ui.theme.Secondary
import com.puc.correios.ui.theme.Surface
import com.puc.correios.ui.theme.SurfaceLight
import com.puc.correios.ui.theme.Yellow
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
        HeaderUpdate()
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
                shape = RoundedCornerShape(
                    bottomStart = CustomDimensions.shape50,
                    bottomEnd = CustomDimensions.shape50
                )
            )
            .padding(horizontal = CustomDimensions.padding24, vertical = CustomDimensions.padding30)
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

@Preview
@Composable
fun HeaderUpdate() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = CustomDimensions.padding24,
                vertical = CustomDimensions.padding10
            ),
    ) {
        val (txtLastUpdate, icon) = createRefs()
        createHorizontalChain(txtLastUpdate, icon, chainStyle = ChainStyle.SpreadInside)

        Text(
            modifier = Modifier.constrainAs(txtLastUpdate) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            style = MaterialTheme.typography.titleSmall,
            color = Secondary,
            text = "Ultimas atualizações"
        )

        TextButton(modifier = Modifier
            .constrainAs(icon) {
                start.linkTo(txtLastUpdate.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, onClick = { /*TODO*/ }) {

            ConstraintLayout {
                val (txtUpdate, iconUpdate) = createRefs()
                createHorizontalChain(txtUpdate, iconUpdate, chainStyle = ChainStyle.Packed)
                Text(
                    modifier = Modifier.constrainAs(txtUpdate) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = Secondary,
                    text = "Atualizar"
                )
                Icon(
                    imageVector = Icons.Filled.Update,
                    modifier = Modifier
                        .constrainAs(iconUpdate) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(start = CustomDimensions.padding5)
                        .size(CustomDimensions.padding14),
                    contentDescription = "Encomenda",
                    tint = Yellow
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ObjectItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = CustomDimensions.padding24,
                vertical = CustomDimensions.padding10
            ),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceLight
        ),
        onClick = {},
        shape = RoundedCornerShape(CustomDimensions.shape30)
    )
    {
        ConstraintLayout(
            modifier = Modifier
                .background(color = SurfaceLight)
                .fillMaxWidth()
                .padding(CustomDimensions.padding16)
        ) {
            val (flightIcon, txtObjectName, txtLastDate) = createRefs()
            createVerticalChain(txtObjectName, txtLastDate, chainStyle = ChainStyle.SpreadInside)


            Icon(
                imageVector = Icons.Filled.FlightTakeoff,
                modifier = Modifier
                    .constrainAs(flightIcon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = CustomDimensions.padding24)
                    .size(CustomDimensions.padding50),
                contentDescription = "Encomenda",
                tint = Yellow
            )

            Text(
                modifier = Modifier.constrainAs(txtObjectName) {
                    top.linkTo(flightIcon.top)
                    bottom.linkTo(txtLastDate.top)
                    start.linkTo(flightIcon.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.titleMedium,
                color = Yellow,
                text = "AA99999999BR"
            )

            Text(
                modifier = Modifier
                    .constrainAs(txtLastDate) {
                        top.linkTo(txtObjectName.top)
                        bottom.linkTo(flightIcon.bottom)
                        start.linkTo(flightIcon.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = CustomDimensions.padding10),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                text = "Ultima data de atualização: 20/02/2023"
            )
        }
    }
}