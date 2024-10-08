package com.example.rickandmorty.presentation.bottom_navigation

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.presentation_base.navigation.favourites
import com.example.presentation_base.navigation.homeTab
import com.example.presentation_base.navigation.tabBarItems
import com.example.presentation_character_details.CharacterDetailsScreen
import com.example.presentation_character_list.CharacterListScreen
import com.example.presentation_character_list.CharacterListViewModel
import com.example.presentation_favourite_characters.FavouriteCharactersScreen
import com.example.presentation_search.SearchScreen
import com.example.presentation_splash_screen.SplashScreen


@Composable
fun BottomNavigationComposable(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val showBottomBar = tabBarItems.any { currentDestination?.startsWith(it.title) == true }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                TabView(tabBarItems, navController)
            }
        }) { _ ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Splash.route,
            modifier = Modifier.fillMaxWidth()
        ) {
            composable(NavigationItem.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(homeTab.title) {
                val characterListViewModel: CharacterListViewModel = hiltViewModel()
                CharacterListScreen(
                    viewModel = characterListViewModel,
                    navController = navController,
                    charactersJson = null
                )
            }
            composable(
                route = "${homeTab.title}/{charactersJson}?",
                arguments = listOf(
                    navArgument("charactersJson") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) { backStackEntry ->
                val charactersJson = backStackEntry.arguments?.getString("charactersJson")
                val characterListViewModel: CharacterListViewModel = hiltViewModel()
                CharacterListScreen(
                    viewModel = characterListViewModel,
                    navController = navController,
                    charactersJson = charactersJson
                )
            }
            composable(favourites.title) {
                FavouriteCharactersScreen(navController = navController)
            }
            composable(
                route = NavigationItem.Detail.route + "/{characterId}/{isFavorite}",
                arguments = listOf(
                    navArgument(name = "characterId") { type = NavType.IntType },
                    navArgument(name = "isFavorite") { type = NavType.BoolType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("characterId") ?: 1
                val isFavorite = backStackEntry.arguments?.getBoolean("isFavorite") ?: false

                CharacterDetailsScreen(id, isFavorite, navController)
            }
            composable(
                route = NavigationItem.Search.route,
            ) { backStackEntry ->
                SearchScreen(navController)
            }
        }
    }
}
