package com.example.presentation_character_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_base.ui.alert_dialog.AlertDialogComposable
import com.example.presentation_base.ui.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_details.ui.CharacterDetailsComposable

@Composable
fun CharacterDetailsScreen(id: Int, isFavorite: Boolean, navController: NavController) {

    val viewModel: CharacterDetailsViewModel = hiltViewModel()

    val display by viewModel.characterDetail.observeAsState()
    val characterDisplay = display?.characterDisplay

    viewModel.fetchCharacters(id, isFavorite)

    var rememberIsFavorite by remember { mutableStateOf(isFavorite) }

    TopBarScaffoldComposable(
        navController = navController,
        titleContent = {
            Text(
                text = stringResource(R.string.character_details_title),
                modifier = Modifier.fillMaxWidth()
            )
        },
        showBackButton = true,
        onBackButtonClicked = {
            if (isFavorite != rememberIsFavorite) {
                handleNavigation(navController, id)
            }
        }
    ) { paddingModifier ->
        when {
            display?.hasError == true -> ShowAlertDialog { navController.popBackStack() }
            characterDisplay != null -> {
                CharacterDetailsComposable(
                    character = characterDisplay,
                    modifier = paddingModifier,
                    isFavorite = rememberIsFavorite,
                    onHeartIconClick = { newFavoriteStatus ->
                        viewModel.onHeartIconClicked(newFavoriteStatus)
                        rememberIsFavorite = newFavoriteStatus
                    }
                )
            }
        }
    }

    BackHandler {
        if (isFavorite != rememberIsFavorite) {
            handleNavigation(navController, id)
        }
        navController.popBackStack()
    }

}

@Composable
fun ShowAlertDialog(onConfirmation: () -> Unit) {
    AlertDialogComposable(
        dialogTitle = "Something went wrong",
        dialogText = "The character could not be displayed",
        onConfirmation = onConfirmation
    )
}

private fun handleNavigation(navController: NavController, id: Int?) {
    id?.let {
        navController.previousBackStackEntry?.savedStateHandle?.set(
            NavigationItem.Detail.STATUS_OF_FAVOURITE_DETAIL_KEY, it
        )
    }
}