package com.example.presentation_search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.Character
import com.example.presentation_base.back_button.HandleBackButton
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_base.ui.search_bar.SearchBarComposable

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchViewModel = hiltViewModel()

    val familyMembers by viewModel.familyMembers.observeAsState()
    val characters by viewModel.characterList.observeAsState()
    var shouldNavigateBack by rememberSaveable { mutableStateOf(false) }

    SearchBarComposable(
        placeholder = stringResource(R.string.search_by_name_placeholder),
        suggestionsList = familyMembers?.let { it } ?: emptyList(),
    ) {
        shouldNavigateBack = true
        viewModel.onSearchClick(it)
    }

    HandleNavigation(navController, characters, shouldNavigateBack)

    HandleBackButton(navController)
}

@Composable
fun HandleNavigation(
    navController: NavController,
    characters: List<Character>?,
    shouldNavigateBack: Boolean
) {
    characters?.let {
        LaunchedEffect(characters, shouldNavigateBack) {
            if (shouldNavigateBack && characters.isNotEmpty()) {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    NavigationItem.Search.SEARCH_BY_NAME_LIST_KEY, characters
                )
                navController.popBackStack()
            }
        }
    }
}