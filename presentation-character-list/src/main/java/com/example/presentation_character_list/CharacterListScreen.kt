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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.presentation_base.navigation.NavigationItem
import com.example.presentation_base.top_bar.TopBarScaffoldComposable
import com.example.presentation_character_list.ui.CharactersComposable

@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel, navController: NavController) {

    val display by viewModel.characterList.collectAsState()

    val listState: LazyListState = rememberLazyListState()

    TopBarScaffoldComposable(
        navController = navController,
        titleContent = {
            Text(
                text = "Characters (${display.numberOfCharacters})",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        showBackButton = false,
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