package com.example.presentation_base.navigation

enum class Screen {
    DETAIL,
    SEARCH,
}

sealed class NavigationItem(val route: String) {
    object Detail: NavigationItem(Screen.DETAIL.name)
    object Search: NavigationItem(Screen.SEARCH.name) {
        const val searchByNameListKey = "searchByNameList"
    }
}