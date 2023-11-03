package com.puc.correios.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.puc.correios.R
import com.puc.correios.components.error.ErrorState
import com.puc.correios.components.loading.LoadingState
import com.puc.correios.components.textfield.TextFieldCustom
import com.puc.correios.core.routes.Routes
import com.puc.correios.core.utils.UiState
import com.puc.correios.feature.home.ui.model.HomeEventsModel
import com.puc.correios.ui.theme.CustomDimensions
import com.puc.correios.ui.theme.Secondary
import com.puc.correios.ui.theme.Surface
import com.puc.correios.ui.theme.SurfaceLight
import com.puc.correios.ui.theme.Yellow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<UiState<List<HomeEventsModel>>>(
        initialValue = UiState.Loading, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    val errorMessage = viewModel.errorSearchCode.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface)
    ) {
        Header(navController, errorMessage.value) { searchCode ->
            viewModel.validateSearchCode(
                searchCode = searchCode,
                onNavigation = { cod ->
                    navController.navigate(Routes.Details.createRoute(cod))
                }
            )
        }

        HeaderUpdate { viewModel.updateUiState() }

        when (uiState) {
            is UiState.Error -> ErrorState { viewModel.updateUiState() }
            UiState.Loading -> LoadingState()
            is UiState.Success -> {
                val homeEventsModel = (uiState as UiState.Success).response
                if (homeEventsModel.isEmpty()) {
                    EmptyEventList()
                } else {
                    EventsList(navController, homeEventsModel) {
                        viewModel.openBrowser()
                    }
                }
            }
        }
    }
}

@Composable
fun Header(
    navController: NavController,
    error: Boolean,
    onClickSearchListener: (searchCode: String) -> Unit
) {
    var searchCode by rememberSaveable { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SurfaceLight, shape = RoundedCornerShape(
                    bottomStart = CustomDimensions.shape50, bottomEnd = CustomDimensions.shape50
                )
            )
            .padding(horizontal = CustomDimensions.padding24, vertical = CustomDimensions.padding30)
    ) {
        val (tfSearch) = createRefs()

        TextFieldCustom(modifier = Modifier.constrainAs(tfSearch) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
        },
            imeAction = ImeAction.Go,
            keyboardActions = KeyboardActions(onGo = {
                navController.navigate(
                    Routes.Details.createRoute(searchCode)
                )
            }),
            onChangeListener = { searchCode = it },
            placeholder = stringResource(R.string.home_text_field_search_placeholder),
            label = stringResource(R.string.home_text_field_search_label),
            error = error,
            maxLength = 13,
            supportText = if (error) {
                stringResource(R.string.home_text_field_search_support_text_error)
            } else {
                stringResource(R.string.home_text_field_search_support_text)
            },
            endIconImageVector = Icons.Filled.Search,
            endIconDescription = stringResource(R.string.home_text_field_search_icon_description),
            endIconListener = { onClickSearchListener(searchCode) })
    }
}

@Composable
fun HeaderUpdate(onClickUpdateItems: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = CustomDimensions.padding24, vertical = CustomDimensions.padding8
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
            text = stringResource(R.string.home_text_field_last_update_title)
        )

        TextButton(modifier = Modifier.constrainAs(icon) {
            start.linkTo(txtLastUpdate.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }, onClick = { onClickUpdateItems() }) {

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
                    text = stringResource(R.string.home_text_field_update_title)
                )
                Icon(imageVector = Icons.Filled.Update,
                    modifier = Modifier
                        .constrainAs(iconUpdate) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(start = CustomDimensions.padding5)
                        .size(CustomDimensions.padding14),
                    contentDescription = stringResource(R.string.home_text_field_update_title),
                    tint = Yellow)
            }
        }
    }
}

@Preview
@Composable
fun EmptyEventList() {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (icon, txtEvent) = createRefs()

        createVerticalChain(icon, txtEvent, chainStyle = ChainStyle.Packed)

        Icon(
            modifier = Modifier
                .size(CustomDimensions.padding50)
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(txtEvent.top)
                },
            imageVector = Icons.Filled.FlightTakeoff,
            contentDescription = stringResource(R.string.home_empty_list_icon_description),
            tint = Secondary
        )

        Text(
            modifier = Modifier
                .padding(top = CustomDimensions.padding14)
                .constrainAs(txtEvent) {
                    top.linkTo(icon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            style = MaterialTheme.typography.titleMedium,
            color = Secondary,
            text = stringResource(R.string.home_empty_list_text_description)
        )
    }
}

@Composable
fun EventsList(
    navController: NavController,
    events: List<HomeEventsModel>,
    onOpenBrowser: () -> Unit
) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (lcEventList, img) = createRefs()

        createVerticalChain(lcEventList, img, chainStyle = ChainStyle.SpreadInside)

        LazyColumn(modifier = Modifier
            .constrainAs(lcEventList) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(img.top)
                height = Dimension.fillToConstraints
            }
            .padding(vertical = CustomDimensions.padding5)) {
            items(events) { event ->
                ObjectItem(event) { cod ->
                    navController.navigate(Routes.Details.createRoute(cod))
                }
            }
        }

        Image(modifier = Modifier
            .constrainAs(img) {
                bottom.linkTo(parent.bottom)
                top.linkTo(lcEventList.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .size(height = CustomDimensions.padding50, width = CustomDimensions.padding150)
            .clickable { onOpenBrowser() },
            painter = painterResource(R.drawable.link_track),
            contentDescription = "icone de link de rastreamento"
        )
    }
}

@Composable
fun ObjectItem(event: HomeEventsModel, onClickItemListener: (cod: String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = CustomDimensions.padding24, vertical = CustomDimensions.padding10
            )
            .clickable { onClickItemListener(event.cod) }, colors = CardDefaults.cardColors(
            containerColor = SurfaceLight
        ), shape = RoundedCornerShape(CustomDimensions.shape30)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(color = SurfaceLight)
                .fillMaxWidth()
                .padding(CustomDimensions.padding16)
        ) {
            val (flightIcon, txtObjectName, txtLastDate) = createRefs()
            createVerticalChain(txtObjectName, txtLastDate, chainStyle = ChainStyle.SpreadInside)


            Icon(imageVector = Icons.Filled.FlightTakeoff,
                modifier = Modifier
                    .constrainAs(flightIcon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = CustomDimensions.padding24)
                    .size(CustomDimensions.padding50),
                contentDescription = "Aviao",
                tint = Yellow)

            Text(
                modifier = Modifier.constrainAs(txtObjectName) {
                    top.linkTo(flightIcon.top)
                    bottom.linkTo(txtLastDate.top)
                    start.linkTo(flightIcon.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }, style = MaterialTheme.typography.titleMedium, color = Yellow, text = event.cod
            )

            Text(modifier = Modifier
                .constrainAs(txtLastDate) {
                    top.linkTo(txtObjectName.top)
                    bottom.linkTo(flightIcon.bottom)
                    start.linkTo(flightIcon.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(top = CustomDimensions.padding10),
                style = MaterialTheme.typography.titleSmall,
                color = Secondary,
                text = stringResource(R.string.home_text_card_last_date, event.lastDate))
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(rememberNavController(), false) {}
}

@Preview
@Composable
fun HeaderWithErrorPreview() {
    Header(rememberNavController(), true) {}
}

@Preview
@Composable
fun HeaderUpdatePreview() {
    HeaderUpdate {}
}

@Preview
@Composable
fun ObjectItemPreview() {
    val event = HomeEventsModel(id = 1, cod = "teste", lastDate = "00/00/0000")
    ObjectItem(event = event, onClickItemListener = {})
}

@Preview
@Composable
fun EventListPreview() {
    val events = listOf(HomeEventsModel(id = 1, cod = "teste", lastDate = "00/00/0000"))
    EventsList(rememberNavController(), events) {}
}