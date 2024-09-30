package com.example.presentation_favourite_characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_favourite_characters.ui.FavouriteCharactersComposable
import com.example.presentation_favourite_characters.ui.FavouriteMessageComposable

@Composable
fun FavouriteCharactersScreen(
    navController: NavController
) {
    val viewModel: FavouriteCharactersViewModel = hiltViewModel()
    val display by viewModel.charactersDisplay.collectAsState()

    if(display.characterList.isEmpty()) {
        FavouriteMessageComposable()
    } else {
        FavouriteCharactersComposable(
            characters = display.characterList,
            onItemClick = { character ->
                navController.navigate(NavigationItem.Detail.route + "/${character.id}")
            },
            onHeartIconClick = { character ->
                viewModel.removeCharacterFromFavourites(character.id)
            }
        )
    }


}