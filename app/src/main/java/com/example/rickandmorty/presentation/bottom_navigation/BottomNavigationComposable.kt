package com.example.rickandmorty.presentation.bottom_navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_character_details.CharacterDetailsScreen
import com.example.presentation_character_list.CharacterListScreen
import com.example.presentation_character_list.CharacterListViewModel
import com.example.presentation_favourite_characters.FavouriteCharactersScreen
import com.example.presentation_search.SearchScreen


@Composable
fun BottomNavigationComposable(navController: NavHostController) {
    val homeTab = TabBarItem("Home", Icons.Filled.Home, Icons.Outlined.Home)
    val favourites = TabBarItem("Favourites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
    val tabBarItems = listOf(homeTab, favourites)

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val showBottomBar = currentDestination != NavigationItem.Detail.route + "/{characterId}"


    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                TabView(tabBarItems, navController)
            }
        }) { _ ->
        NavHost(
            navController = navController,
            startDestination = homeTab.title,
            modifier = Modifier.fillMaxWidth()
        ) {
            composable(homeTab.title) {
                val characterListViewModel: CharacterListViewModel = hiltViewModel()
                CharacterListScreen(
                    characterListViewModel,
                    navController,
                )
            }
            composable(favourites.title) {
                FavouriteCharactersScreen(navController = navController)
            }
            composable(
                route = NavigationItem.Detail.route + "/{characterId}",
                arguments = listOf(
                    navArgument(name = "characterId") { type = NavType.IntType },
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("characterId") ?: 1

                CharacterDetailsScreen(id, navController)
            }
            composable(
                route = NavigationItem.Search.route,
            ) { backStackEntry ->
                SearchScreen(navController)
            }
        }
    }
}
