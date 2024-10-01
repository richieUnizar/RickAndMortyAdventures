package com.example.presentation_character_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.domain.characters.Character
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_base.navigation.observeLiveData
import com.example.presentation_base.ui.error_screen.ErrorMessageComposable
import com.example.presentation_base.ui.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_list.ui.CharactersComposable

@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel, navController: NavController) {
    val display by viewModel.characterList.collectAsState()
    val hasError by viewModel.hasError.collectAsState()
    val filterByNameList = observeLiveData<List<Character>>(navController, NavigationItem.Search.searchByNameListKey)
    val listState: LazyListState = rememberLazyListState()

    LaunchedEffect(filterByNameList) {
        filterByNameList.value?.let { characters ->
            viewModel.updateCharacterList(characters)
        }
    }

    if (hasError) {
        ErrorMessageComposable()
    } else {
        CharacterListContent(
            navController = navController,
            display = display,
            listState = listState,
            viewModel = viewModel
        )
    }
}

@Composable
fun CharacterListContent(
    navController: NavController,
    display: CharactersDisplay,
    listState: LazyListState,
    viewModel: CharacterListViewModel
) {
    TopBarScaffoldComposable(
        navController = navController,
        titleContent = {
            Text(
                text = "Characters (${display.numberOfCharacters})",
                modifier = Modifier.fillMaxWidth()
            )
        },
        showBackButton = false,
        showSearchButton = true,
        onSearchClicked = {
            navController.navigate(NavigationItem.Search.route)
        }
    ) { paddingModifier ->
        CharactersComposable(
            listState = listState,
            characters = display.characterList,
            onItemClick = { character ->
                navController.navigate(NavigationItem.Detail.route + "/${character.id}")
            },
            onHeartIconClick = { isHeartSelected, character ->
                viewModel.onHeartIconClicked(isHeartSelected, character)
            },
            modifier = paddingModifier
        )

        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .collect { index ->
                    viewModel.loadNextPage(index)
                }
        }
    }
}