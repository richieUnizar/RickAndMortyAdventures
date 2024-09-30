package com.example.presentation_search

import com.example.presentation_base.search_bar.SearchBarComposable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.presentation_base.navigation.NavigationItem

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchViewModel = hiltViewModel()

    val familyMembers by viewModel.familyMembers.collectAsState()
    val characters by viewModel.characterList.collectAsState()
    var shouldNavigateBack by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(characters, shouldNavigateBack) {
        if (shouldNavigateBack && characters.isNotEmpty()) {
            navController.previousBackStackEntry?.savedStateHandle?.set(
                NavigationItem.Search.searchByNameListKey, characters
            )
            navController.popBackStack()
        }
    }

    viewModel.characterList.collectAsState()

    SearchBarComposable(
        placeholder = stringResource(R.string.search_by_name_placeholder),
        suggestionsList = familyMembers,
    ) {
        shouldNavigateBack = true
        viewModel.onSearchClick(it)
    }
}

