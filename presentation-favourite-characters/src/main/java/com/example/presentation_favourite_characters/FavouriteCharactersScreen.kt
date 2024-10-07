package com.example.presentation_favourite_characters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_base.ui.top_bar.TopBarScaffoldComposable
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
        TopBarScaffoldComposable(
            navController = navController,
            titleContent = {
                Text(
                    text = stringResource(R.string.favourite_characters_title),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            showBackButton = false,
        ) { paddingModifier ->
            FavouriteCharactersComposable(
                characters = display.characterList,
                onItemClick = { character ->
                    navController.navigate(NavigationItem.Detail.route + "/${character.id}/${character.isInFavourites}")
                },
                onHeartIconClick = { character ->
                    viewModel.removeCharacterFromFavourites(character.id)
                },
                modifier = paddingModifier
            )
        }
    }


}