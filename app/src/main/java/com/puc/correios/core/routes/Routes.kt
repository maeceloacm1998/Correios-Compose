package com.puc.correios.core.routes

sealed class Routes(val route: String) {
    object Login: Routes("login")
    object Home: Routes("home")
    object Details: Routes("details/{cod}") {
        fun createRoute(cod: String) = "details/$cod"
    }
}