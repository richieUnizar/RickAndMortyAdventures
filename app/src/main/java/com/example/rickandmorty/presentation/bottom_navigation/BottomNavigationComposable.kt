package com.example.rickandmorty.presentation.bottom_navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_character_details.CharacterDetailsScreen
import com.example.presentation_character_list.CharacterListScreen
import com.example.presentation_character_list.CharacterListViewModel


@Composable
fun BottomNavigationComposable(navController: NavHostController) {
    val homeTab = TabBarItem("Home", Icons.Filled.Home, Icons.Outlined.Home)
    val favourites = TabBarItem("Favourites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
    val settingsTab = TabBarItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    val tabBarItems = listOf(homeTab, favourites, settingsTab)

    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = homeTab.title,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(homeTab.title) {
                val characterListViewModel: CharacterListViewModel = hiltViewModel()
                CharacterListScreen(
                    characterListViewModel,
                    navController
                )
            }
            composable(favourites.title) {
                Text(favourites.title)
            }
            composable(settingsTab.title) {
                Text(settingsTab.title)
            }
            composable(
                route = NavigationItem.Detail.route + "/{characterId}",
                arguments = listOf(
                    navArgument(name = "characterId") { type = NavType.IntType },
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("characterId") ?: 1

                CharacterDetailsScreen(id)
            }
        }
    }
}
