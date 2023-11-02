package com.puc.correios.components.error

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.puc.correios.R
import com.puc.correios.ui.theme.CustomDimensions
import com.puc.correios.ui.theme.Secondary
import com.puc.correios.ui.theme.SurfaceLight

@Composable
fun ErrorState(onClickRetryListener: () -> Unit) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (icon, txtEvent, btRetry) = createRefs()

        createVerticalChain(icon, txtEvent, btRetry, chainStyle = ChainStyle.Packed)

        Icon(
            modifier = Modifier
                .size(CustomDimensions.padding50)
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(txtEvent.top)
                },
            imageVector = Icons.Filled.SignalWifiConnectedNoInternet4,
            contentDescription = stringResource(R.string.error_state_icon_description),
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
            text = stringResource(R.string.error_state_text_description_title)
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = CustomDimensions.padding24, vertical = CustomDimensions.padding30
            )
            .constrainAs(btRetry) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, onClick = { onClickRetryListener() }, colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceLight
        )
        ) {
            Text(
                style = MaterialTheme.typography.titleSmall,
                color = Secondary,
                text = stringResource(R.string.error_state_text_retry_title)
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ErrorState {}
}