package com.example.presentation_base.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen {
    SPLASH,
    DETAIL,
    SEARCH,
}

sealed class NavigationItem(val route: String) {
    object Splash: NavigationItem(Screen.SPLASH.name) {
        const val SPLASH_KEY = "SplashScreen"
    }
    object Detail: NavigationItem(Screen.DETAIL.name) {
        const val STATUS_OF_FAVOURITE_DETAIL_KEY = "statusOfFavouriteDetail"
    }
    object Search: NavigationItem(Screen.SEARCH.name) {
        const val SEARCH_BY_NAME_LIST_KEY = "searchByNameList"
    }
}

val homeTab = TabBarItem("Home", Icons.Filled.Home, Icons.Outlined.Home)
val favourites = TabBarItem("Favourites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
val tabBarItems = listOf(homeTab, favourites)


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)
