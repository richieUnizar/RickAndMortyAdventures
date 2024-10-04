package com.example.presentation_character_details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation_base.alert_dialog.AlertDialogComposable
import com.example.presentation_base.ui.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_details.ui.CharacterDetailsComposable

@Composable
fun CharacterDetailsScreen(id: Int, navController: NavController) {

    val viewModel: CharacterDetailsViewModel = hiltViewModel()

    val display by viewModel.characterDetail.observeAsState()
    val characterDisplay = display?.characterDisplay

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
        when {
            display?.hasError == true -> ShowAlertDialog { navController.popBackStack() }
            characterDisplay != null -> {
                CharacterDetailsComposable(
                    character = characterDisplay,
                    modifier = paddingModifier
                )
            }

        }
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