package com.puc.correios.core.routes

sealed class Routes(val route: String) {
    object Login: Routes("Login")
    object Home: Routes("Home")
    object Details: Routes("Details")
}