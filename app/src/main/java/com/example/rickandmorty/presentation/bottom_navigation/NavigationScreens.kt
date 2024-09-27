package com.example.rickandmorty.presentation.bottom_navigation

enum class Screen {
    DETAIL,
}

sealed class NavigationItem(val route: String) {
    object Detail: NavigationItem(Screen.DETAIL.name)
}