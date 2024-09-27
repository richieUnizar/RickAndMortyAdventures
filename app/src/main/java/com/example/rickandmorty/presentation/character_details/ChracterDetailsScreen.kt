package com.example.rickandmorty.presentation.character_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.presentation.character_details.ui.CharacterDetailsComposable

@Composable
fun CharacterDetailsScreen(id: Int) {

    val viewModel: CharacterDetailsViewModel = hiltViewModel()

    val character by viewModel.characterDetail.collectAsState()

    viewModel.fetchCharacters(id)

    CharacterDetailsComposable(character)

}