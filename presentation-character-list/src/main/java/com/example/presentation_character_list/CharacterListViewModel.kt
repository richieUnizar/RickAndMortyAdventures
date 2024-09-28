package com.example.presentation_character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.characters.Characters
import com.example.domain.characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _characterList = MutableStateFlow(CharactersDisplay(emptyList()))
    val characterList: StateFlow<CharactersDisplay> = _characterList

    private var pageToLoad = 1
    private var numberOfPages: Int? = null

    init {
        fetchCharacters(pageToLoad)
    }

    fun loadNextPage(index: Int){
        numberOfPages?.let {
            val isNearEndOfList = index == _characterList.value.characterList.count() - 10
            val shouldLoadNextPage = pageToLoad <= it

            if (isNearEndOfList && shouldLoadNextPage) {
                fetchCharacters(pageToLoad)
            }
        }

    }

    private fun fetchCharacters(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            charactersUseCase.getCharacters(page).fold(
                onSuccess = { characters ->
                    pageToLoad++
                    numberOfPages = characters.info.numberOfPages

                    val display: CharactersDisplay = characters.toDisplay()
                    _characterList.value = display
                },
                onFailure = { error ->
                    Log.d("RickAndMorty", error.message ?: "Unknown error")
                }
            )
        }
    }
}