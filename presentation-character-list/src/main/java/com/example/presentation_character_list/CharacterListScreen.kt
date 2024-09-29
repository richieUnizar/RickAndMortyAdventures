package com.example.presentation_character_list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.navigation.NavController
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_character_list.ui.CharactersComposable

@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel, navController: NavController) {

    val display by viewModel.characterList.collectAsState()

    val listState: LazyListState = rememberLazyListState()

    CharactersComposable(
        listState = listState,
        characters = display.characterList,
        onItemClick = { character ->
            navController.navigate(NavigationItem.Detail.route + "/${character.id}")
        },
        onHeartIconClick = { isHeartSelected, character ->
            viewModel.onHeartIconClicked(isHeartSelected, character)
        }
    )

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                viewModel.loadNextPage(index)
            }
    }
}