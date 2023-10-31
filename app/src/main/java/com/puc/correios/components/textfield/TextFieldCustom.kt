package com.puc.correios.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.puc.correios.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCustom(
    label: String,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggle: Boolean = false,
    maxLines: Int = 1,
    modifier: Modifier? = null,
    onChangeListener: (text: String) -> Unit,
    endIconImageVector: ImageVector? = null,
    endIconDescription: String = "",
    endIconListener: (() -> Unit)? = null
) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(isPasswordToggle) }

    OutlinedTextField(
        value = text,
        modifier = modifier ?: Modifier.fillMaxWidth(),
        onValueChange = {
            text = it
            onChangeListener(it)
        },
        label = { Text(text = label, color = DarkBlue) },
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = DarkBlue
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        maxLines = maxLines,
        trailingIcon = {
            if (isPasswordToggle) {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Visibility else Icons.Outlined.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            } else {
                IconButton(onClick = { endIconListener?.let { it() } }) {
                    endIconImageVector?.let {
                        Icon(
                            imageVector = it, contentDescription = endIconDescription
                        )
                    }
                }
            }
        },
    )
}