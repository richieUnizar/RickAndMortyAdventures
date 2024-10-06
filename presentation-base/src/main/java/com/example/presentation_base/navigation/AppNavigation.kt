package com.example.presentation_base.navigation

enum class Screen {
    DETAIL,
    SEARCH,
}

sealed class NavigationItem(val route: String) {
    object Detail: NavigationItem(Screen.DETAIL.name) {
        const val STATUS_OF_FAVOURITE_DETAIL_KEY = "statusOfFavouriteDetail"
    }
    object Search: NavigationItem(Screen.SEARCH.name) {
        const val SEARCH_BY_NAME_LIST_KEY = "searchByNameList"
    }
}