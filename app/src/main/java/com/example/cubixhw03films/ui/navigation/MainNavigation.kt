package com.example.cubixhw03films.ui.navigation

sealed class MainNavigation(val route: String) {
    object FilmsScreen : MainNavigation("filmsscreen")
}