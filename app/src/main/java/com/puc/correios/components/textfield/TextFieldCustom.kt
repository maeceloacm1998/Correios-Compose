package com.puc.correios.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.puc.correios.ui.theme.Secondary
import com.puc.correios.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCustom(
    label: String,
    placeholder: String = "",
    supportText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggle: Boolean = false,
    maxLines: Int = 1,
    modifier: Modifier? = null,
    onChangeListener: (text: String) -> Unit,
    endIconImageVector: ImageVector? = null,
    endIconDescription: String = "",
    endIconListener: (() -> Unit)? = null,
    error: Boolean = true
) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(isPasswordToggle) }

    OutlinedTextField(
        text,
        {
            text = it
            onChangeListener(it)
        },
        modifier ?: Modifier.fillMaxWidth(),
        label = {
            Text(
                text = label,
                color = if (error) MaterialTheme.colorScheme.error else Yellow
            )
        },
        placeholder = { Text(placeholder, color = Secondary) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Yellow,
            errorLabelColor = MaterialTheme.colorScheme.error
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        maxLines = maxLines,
        isError = error,
        supportingText = {
            Text(
                supportText,
                color = if (error) MaterialTheme.colorScheme.error else Yellow
            )
        },
        trailingIcon = {
            if (isPasswordToggle) {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Visibility else Icons.Outlined.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = description,
                        tint = if (error) MaterialTheme.colorScheme.error else Yellow
                    )
                }
            } else {
                IconButton(onClick = { endIconListener?.let { it() } }) {
                    endIconImageVector?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = endIconDescription,
                            tint = if (error) MaterialTheme.colorScheme.error else Yellow
                        )
                    }
                }
            }
        },
    )
}