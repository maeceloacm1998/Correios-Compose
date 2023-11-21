package com.puc.correios.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.puc.correios.components.error.ErrorState
import com.puc.correios.components.loading.LoadingState
import com.puc.correios.components.toolbar.ToolbarCustom
import com.puc.correios.core.utils.UiState
import com.puc.correios.feature.details.data.network.response.TrackingResponse
import com.puc.correios.ui.theme.CustomDimensions
import com.puc.correios.ui.theme.Secondary
import com.puc.correios.ui.theme.Surface
import com.puc.correios.ui.theme.SurfaceLight
import com.puc.correios.ui.theme.Yellow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    cod: String?,
    viewModel: DetailsViewModel = koinViewModel()
) {
    LaunchedEffect(cod) {
        cod?.let { viewModel.fetchTracking(it) }
    }

    Column(
        modifier = Modifier
            .background(Surface)
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

        when (uiState) {
            is UiState.Error -> ErrorState { cod?.let { viewModel.fetchTracking(it) } }
            UiState.Loading -> LoadingState()
            is UiState.Success -> {
                val response = (uiState as UiState.Success).response
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Surface)
                ) {
                    DetailsToolbar(response.code) {
                        navController.popBackStack()
                    }
                    TitleBar(
                        checked = viewModel.isNotificationEnabled(),
                        onClickNotificationListener = {
                            viewModel.handleNotificationEnabled(it)
                        })
                    EventList(response.events)
                }
            }
        }
    }
}

@Composable
fun DetailsToolbar(cod: String, onBackListener: () -> Unit) {
    ToolbarCustom(title = cod, onBackListener = onBackListener)
}

@Composable
fun TitleBar(
    onClickNotificationListener: (checked: Boolean) -> Unit,
    checked: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(
                top = CustomDimensions.padding10,
                end = CustomDimensions.padding24,
                start = CustomDimensions.padding24
            )
            .fillMaxWidth()
    ) {
        val (txtTitle, swToggle, txtToggle) = createRefs()
        var notificationToggle by rememberSaveable { mutableStateOf(checked) }

        Text(
            modifier = Modifier
                .constrainAs(txtTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            style = MaterialTheme.typography.titleMedium,
            color = Secondary,
            text = "Trajeto do seu pedido"
        )

        Switch(
            modifier = Modifier
                .padding(vertical = CustomDimensions.padding5)
                .constrainAs(swToggle) {
                    top.linkTo(txtTitle.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            checked = notificationToggle,
            onCheckedChange = {
                notificationToggle = it
                onClickNotificationListener(it)
            }
        )

        Text(
            modifier = Modifier
                .padding(horizontal = CustomDimensions.padding10)
                .constrainAs(txtToggle) {
                    start.linkTo(swToggle.end)
                    top.linkTo(swToggle.top)
                    bottom.linkTo(swToggle.bottom)
                },
            style = MaterialTheme.typography.titleMedium,
            color = Secondary,
            text = "Notificaçao"
        )
    }
}


@Composable
fun EventList(eventList: List<TrackingResponse.Event>) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(CustomDimensions.padding24)
    ) {
        val (lcEventList) = createRefs()
        LazyColumn(modifier = Modifier
            .constrainAs(lcEventList) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
            .padding(vertical = CustomDimensions.padding5)) {
            items(eventList) { event ->
                TrackingItem(event)
            }
        }
    }
}

@Composable
fun TrackingItem(events: TrackingResponse.Event) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CustomDimensions.padding10)
    ) {
        val (bxLineTop, bxIcon, txtStatus, txtLocalization, txtDateAndHour) = createRefs()
        createVerticalChain(
            txtStatus,
            txtLocalization,
            txtDateAndHour,
            chainStyle = ChainStyle.Packed
        )

        Box(modifier = Modifier
            .background(Secondary)
            .width(CustomDimensions.padding5)
            .constrainAs(bxLineTop) {
                start.linkTo(bxIcon.start)
                end.linkTo(bxIcon.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            })

        Box(
            modifier = Modifier
                .background(Yellow, shape = CircleShape)
                .constrainAs(bxIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            Icon(
                modifier = Modifier.padding(CustomDimensions.padding10),
                imageVector = Icons.Filled.FlightTakeoff,
                contentDescription = "icon",
                tint = SurfaceLight
            )
        }

        Text(
            modifier = Modifier
                .padding(
                    horizontal = CustomDimensions.padding14,
                )
                .constrainAs(txtStatus) {
                    start.linkTo(bxIcon.end)
                    top.linkTo(bxIcon.top)
                },
            style = MaterialTheme.typography.titleMedium,
            color = Secondary,
            text = events.status
        )

        Text(
            modifier = Modifier
                .padding(
                    horizontal = CustomDimensions.padding14
                )
                .constrainAs(txtLocalization) {
                    start.linkTo(bxIcon.end)
                    top.linkTo(bxIcon.top)
                },
            style = MaterialTheme.typography.titleSmall,
            color = Secondary,
            text = events.location
        )

        Text(
            modifier = Modifier
                .padding(
                    start = CustomDimensions.padding14,
                    end = CustomDimensions.padding14,
                    bottom = CustomDimensions.padding50
                )
                .constrainAs(txtDateAndHour) {
                    start.linkTo(bxIcon.end)
                    top.linkTo(bxIcon.top)
                    bottom.linkTo(parent.bottom)
                },
            style = MaterialTheme.typography.titleSmall,
            color = Secondary,
            text = "${events.date} ${events.time}"
        )
    }
}

@Preview
@Composable
fun DetailsToolbarPreview() {
    DetailsToolbar(cod = "teste", onBackListener = {})
}

@Preview
@Composable
fun TitleBarPreview() {
    TitleBar(checked = false, onClickNotificationListener = {})
}

@Preview
@Composable
fun EventListPreview() {
    val eventList: List<TrackingResponse.Event> = listOf(
        TrackingResponse.Event(
            date = "23/10/2023",
            time = "09:35:29",
            location = "BELO HORIZONTE - MG",
            status = "Objeto saiu para entrega ao destinatário"
        ),
        TrackingResponse.Event(
            date = "23/10/2023",
            time = "09:35:29",
            location = "BELO HORIZONTE - MG",
            status = "Objeto saiu para entrega ao destinatário"
        ),
        TrackingResponse.Event(
            date = "23/10/2023",
            time = "09:35:29",
            location = "BELO HORIZONTE - MG",
            status = "Objeto saiu para entrega ao destinatário"
        ),
    )
    EventList(eventList)
}

@Preview
@Composable
fun TrackingItemPreview() {
    TrackingItem(
        events = TrackingResponse.Event(
            date = "23/10/2023",
            time = "09:35:29",
            location = "BELO HORIZONTE - MG",
            status = "Objeto saiu para entrega ao destinatário"
        )
    )
}