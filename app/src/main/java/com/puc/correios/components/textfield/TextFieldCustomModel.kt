package com.puc.correios.components.textfield

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

data class TextFieldCustomModel(
    val label: String = "",
    val placeholder: String = "",
    val keyboardType: KeyboardType = KeyboardType.Text,
    val onChangeListener: (text: String) -> Unit,
    val isPasswordToggle: Boolean = false,
    val modifier: Modifier? = null
)