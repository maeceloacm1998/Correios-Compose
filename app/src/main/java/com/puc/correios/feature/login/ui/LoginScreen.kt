package com.puc.correios.feature.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AppRegistration
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.puc.correios.R
import com.puc.correios.components.textfield.TextFieldCustom
import com.puc.correios.ui.theme.DarkBlue
import com.puc.correios.ui.theme.Yellow
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<LoginUiState>(
        initialValue = LoginUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is LoginUiState.Error -> {}

        LoginUiState.Loading -> {
            CircularProgressIndicator()
        }

        is LoginUiState.Success -> {
            val res = (uiState as LoginUiState.Success).response
            Screen()
        }
    }
}

@Composable()
fun Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginFields()
        StayConnected()
        Options()
    }
}

@Composable
fun LoginFields() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    ConstraintLayout() {
        val (tfEmail, tfPassword, btSubmit) = createRefs()

        TextFieldCustom(
            label = stringResource(R.string.login_text_field_email_label),
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
    }
}

@Composable
fun StayConnected() {
    var stayConnected by rememberSaveable { mutableStateOf(false) }

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (textConnect, tgStayConnected) = createRefs()
        createHorizontalChain(textConnect, tgStayConnected, chainStyle = ChainStyle.SpreadInside)

        Text(text = "Manter contectado",
            style = MaterialTheme.typography.titleSmall,
            color = DarkBlue,
            modifier = Modifier
                .padding(start = 24.dp)
                .constrainAs(textConnect) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
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

    }
}

@Composable
fun Options() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        val (btRegister, btRecoverPassword) = createRefs()
        createHorizontalChain(btRecoverPassword, btRegister, chainStyle = ChainStyle.SpreadInside)

        TextButton(onClick = {},
            modifier = Modifier
                .constrainAs(btRecoverPassword) {
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
                text = "Cadastrar",
                style = MaterialTheme.typography.titleMedium,
                color = DarkBlue
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun Preview() {
    Screen()
}