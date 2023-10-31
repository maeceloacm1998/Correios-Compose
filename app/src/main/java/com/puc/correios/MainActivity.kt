package com.puc.correios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.puc.correios.core.network.RetrofitDependencyInjection
import com.puc.correios.core.routes.Routes
import com.puc.correios.feature.home.ui.HomeScreen
import com.puc.correios.feature.login.data.di.LoginDependencyInjection
import com.puc.correios.feature.login.ui.LoginScreen
import com.puc.correios.ui.theme.CorreiosTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        koinStarted()

        setContent {
            CorreiosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navigationRoute()
                }
            }
        }
    }

    private fun koinStarted() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(
                listOf(
                    LoginDependencyInjection.loginModules,
                )
            )
        }
    }

    @Composable
    private fun navigationRoute() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(Routes.Login.route) { LoginScreen(navController = navController) }
            composable(Routes.Home.route) { HomeScreen() }
            composable(Routes.Details.route) {}
        }
    }
}