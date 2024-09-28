package com.example.presentation_base.navigation

enum class Screen {
    DETAIL,
}

sealed class NavigationItem(val route: String) {
    object Detail: NavigationItem(Screen.DETAIL.name)
}