package com.example.presentation_character_details

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
import com.example.presentation_base.ui.error_screen.ErrorMessageComposable
import com.example.presentation_base.ui.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_details.ui.CharacterDetailsComposable

@Composable
fun CharacterDetailsScreen(id: Int, navController: NavController) {

    val viewModel: CharacterDetailsViewModel = hiltViewModel()

    val character by viewModel.characterDetail.collectAsState()
    val hasError by viewModel.hasError.collectAsState()

    viewModel.fetchCharacters(id)

    TopBarScaffoldComposable(
        navController = navController,
        titleContent = {
            Text(
                text = stringResource(R.string.character_details_title),
                modifier = Modifier.fillMaxWidth()
            )
        },
        showBackButton = true,
    ) { paddingModifier ->
        if (hasError) {
            ErrorMessageComposable()
        } else {
            CharacterDetailsComposable(
                character = character,
                modifier = paddingModifier
            )
        }
    }

}