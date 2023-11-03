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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.puc.correios.core.routes.Routes
import com.puc.correios.feature.commons.database.events.di.EventsDatabaseDependencyInjection
import com.puc.correios.feature.details.data.di.DetailsDependencyInjection
import com.puc.correios.feature.details.ui.DetailsScreen
import com.puc.correios.feature.home.data.di.HomeDependencyInjection
import com.puc.correios.feature.home.ui.HomeScreen
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
                    NavigationRoute()
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
                    EventsDatabaseDependencyInjection.eventsModules,
                    HomeDependencyInjection.homeModules,
                    DetailsDependencyInjection.detailsModules
                )
            )
        }
    }

    @Composable
    private fun NavigationRoute() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(Routes.Home.route) { HomeScreen(navController = navController) }
            composable(
                Routes.Details.route,
                arguments = listOf(navArgument("cod") { type = NavType.StringType })
            ) { navBackStackEntry ->
                DetailsScreen(navController, navBackStackEntry.arguments?.getString("cod"))
            }
        }
    }
}