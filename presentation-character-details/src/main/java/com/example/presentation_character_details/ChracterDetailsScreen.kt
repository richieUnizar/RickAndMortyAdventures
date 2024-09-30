package com.example.presentation_character_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation_base.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_details.ui.CharacterDetailsComposable

@Composable
fun CharacterDetailsScreen(id: Int, navController: NavController) {

    val viewModel: CharacterDetailsViewModel = hiltViewModel()

    val character by viewModel.characterDetail.collectAsState()

    viewModel.fetchCharacters(id)

    TopBarScaffoldComposable(
        navController = navController,
        title = stringResource(R.string.character_details) ,
        showBackButton = true,
    ) { paddingModifier ->
        CharacterDetailsComposable(
            character = character,
            modifier = paddingModifier
        )
    }

}