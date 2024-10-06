package com.example.presentation_character_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.domain.model.Character
import com.example.presentation_base.ui.loading.LoadingIndicatorComposable
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_base.navigation.NavigationItem.Detail.STATUS_OF_FAVOURITE_DETAIL_KEY
import com.example.presentation_base.navigation.NavigationItem.Search.SEARCH_BY_NAME_LIST_KEY
import com.example.presentation_base.navigation.observeLiveData
import com.example.presentation_base.ui.error_screen.ErrorMessageComposable
import com.example.presentation_base.ui.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_list.ui.CharactersComposable

@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel, navController: NavController) {

    val display by viewModel.characters.observeAsState()

    val filterByNameList =
        observeLiveData<List<Character>>(navController, SEARCH_BY_NAME_LIST_KEY)

    val favouriteDetailsChanged =
        observeLiveData<Int>(navController, STATUS_OF_FAVOURITE_DETAIL_KEY)

    val listState: LazyListState = rememberLazyListState()

    LaunchedEffect(filterByNameList, favouriteDetailsChanged) {
        filterByNameList.value?.let { characters ->
            viewModel.updateCharacterList(characters)
        }
        favouriteDetailsChanged.value?.let { id ->
            viewModel.heartIconDetailChanged(id)
        }
    }

    display?.let { safeDisplay ->
        when {
            safeDisplay.loading -> LoadingIndicatorComposable()
            safeDisplay.hasError -> ErrorMessageComposable()
            else -> CharacterListContent(
                navController = navController,
                display = safeDisplay,
                listState = listState,
                viewModel = viewModel,
            )
        }
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
                navController.navigate(NavigationItem.Detail.route + "/${character.id}/${character.isInFavourites}")
            },
            onHeartIconClick = { isHeartSelected, character ->
                viewModel.onHeartIconClicked(character, isHeartSelected)
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