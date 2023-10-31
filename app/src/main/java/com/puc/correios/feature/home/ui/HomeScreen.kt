package com.puc.correios.feature.home.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.puc.correios.R
import com.puc.correios.components.textfield.TextFieldCustom
import com.puc.correios.ui.theme.DarkBlue

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            .background(Color.White)
    ) {
        val (divider, tfSearch) = createRefs()

        TextFieldCustom(modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .constrainAs(tfSearch) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(divider.top)
                width = Dimension.fillToConstraints
            },
            onChangeListener = { search = it },
            placeholder = stringResource(R.string.home_text_field_search_placeholder),
            label = stringResource(R.string.home_text_field_search_label),
            endIconImageVector = Icons.Filled.Search,
            endIconDescription = stringResource(R.string.home_text_field_search_icon_description),
            endIconListener = { Log.d("opa", "clicou") })

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(2.dp)
                .constrainAs(divider) {
                    top.linkTo(tfSearch.bottom)
                    bottom.linkTo(parent.bottom)
                },
            color = DarkBlue,
            thickness = 1.dp
        )
    }
}

@Preview
@Composable
fun Preview() {
    HomeScreen()
}