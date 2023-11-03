package com.puc.correios.components.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.puc.correios.ui.theme.Secondary
import com.puc.correios.ui.theme.SurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarCustom(title: String, onBackListener: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = title,
                color = Secondary
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = SurfaceLight,
            navigationIconContentColor = Secondary
        ),
        navigationIcon = {
            IconButton(onClick = { onBackListener() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "voltar"
                )
            }
        })
}

@Preview
@Composable
fun ToolbarPreview() {
    ToolbarCustom(title = "Teste") {}
}