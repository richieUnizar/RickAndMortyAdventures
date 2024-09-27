package com.example.rickandmorty.presentation.character_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.character.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _characterDetail = MutableStateFlow(CharacterDisplay(0, "", "", "", ""))
    val characterDetail: StateFlow<CharacterDisplay> = _characterDetail

    fun fetchCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterUseCase.getCharacter(id).fold(
                onSuccess = { character ->
                    _characterDetail.value = character.toDisplay()
                },
                onFailure = { error ->
                    Log.d("RickAndMorty", error.message ?: "Unknown error")
                }
            )
        }
    }
}