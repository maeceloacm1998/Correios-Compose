package com.puc.correios.feature.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AppRegistration
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.puc.correios.R
import com.puc.correios.components.textfield.TextFieldCustom
import com.puc.correios.ui.theme.DarkBlue
import com.puc.correios.ui.theme.Yellow

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var stayConnected by rememberSaveable { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (tfEmail, tfPassword, btSubmit, textConnect, tgStayConnected, btRegister, btRecoverPassword) = createRefs()
        createHorizontalChain(textConnect, tgStayConnected, chainStyle = ChainStyle.SpreadInside)
        createHorizontalChain(btRecoverPassword, btRegister, chainStyle = ChainStyle.SpreadInside)
        createVerticalChain(
            tfEmail,
            tfPassword,
            btSubmit,
            textConnect,
            btRecoverPassword,
            chainStyle = ChainStyle.Packed
        )

        TextFieldCustom(label = stringResource(R.string.login_text_field_email_label),
            placeholder = stringResource(R.string.login_text_field_email_placeholder),
            keyboardType = KeyboardType.Email,
            onChangeListener = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp
                )
                .constrainAs(tfEmail) {
                    top.linkTo(parent.top)
                    bottom.linkTo(tfPassword.top)
                })

        TextFieldCustom(
            label = stringResource(R.string.login_text_field_password_label),
            keyboardType = KeyboardType.Password,
            onChangeListener = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(tfPassword) {
                    top.linkTo(tfEmail.bottom)
                    bottom.linkTo(btSubmit.top)
                },
            isPasswordToggle = true
        )

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .constrainAs(btSubmit) {
                    top.linkTo(tfPassword.bottom)
                    bottom.linkTo(textConnect.top)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow
            )
        ) {
            Text(
                stringResource(R.string.login_button_text),
                color = DarkBlue,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Text(text = "Manter contectado",
            style = MaterialTheme.typography.titleSmall,
            color = DarkBlue,
            modifier = Modifier
                .padding(start = 24.dp)
                .constrainAs(textConnect) {
                    top.linkTo(btSubmit.bottom)
                    bottom.linkTo(btRecoverPassword.top)
                })

        Switch(checked = stayConnected,
            onCheckedChange = { stayConnected = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = DarkBlue,
                checkedTrackColor = Yellow,
                uncheckedThumbColor = DarkBlue,
                uncheckedTrackColor = MaterialTheme.colorScheme.outlineVariant,
            ),
            modifier = Modifier
                .padding(end = 24.dp)
                .constrainAs(tgStayConnected) {
                    top.linkTo(textConnect.top)
                    bottom.linkTo(textConnect.bottom)
                })

        TextButton(onClick = {},
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(btRecoverPassword) {
                    top.linkTo(textConnect.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }) {
            Icon(
                imageVector = Icons.Outlined.LockOpen,
                contentDescription = "",
                tint = DarkBlue,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "Esqueci senha",
                style = MaterialTheme.typography.titleMedium,
                color = DarkBlue,
            )
        }

        TextButton(onClick = {}, modifier = Modifier
            .padding(top = 16.dp)
            .constrainAs(btRegister) {
                top.linkTo(btRecoverPassword.top)
                end.linkTo(parent.end)
                bottom.linkTo(btRecoverPassword.bottom)
                width = Dimension.fillToConstraints
            }) {
            Icon(
                imageVector = Icons.Outlined.AppRegistration,
                contentDescription = "",
                tint = DarkBlue,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "Cadastrar", style = MaterialTheme.typography.titleMedium, color = DarkBlue
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun Preview() {
    LoginScreen(rememberNavController())
}