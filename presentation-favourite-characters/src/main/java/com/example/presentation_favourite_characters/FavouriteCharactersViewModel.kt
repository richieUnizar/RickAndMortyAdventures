package com.example.presentation_favourite_characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Favourites.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.RemoveToFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.characters.Character

@HiltViewModel
class FavouriteCharactersViewModel @Inject constructor(
    private var getFavouriteCharactersUseCase: GetFavouriteCharactersUseCase,
    private val removeToFavouriteUseCase: RemoveToFavouriteUseCase,
) : ViewModel() {

    private val _charactersDisplay = MutableStateFlow(CharactersDisplay(emptyList()))
    val charactersDisplay: StateFlow<CharactersDisplay> = _charactersDisplay

    private lateinit var characters: List<Character>

    init {
        fetchFavouriteCharacters()
    }

    private fun fetchFavouriteCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavouriteCharactersUseCase.getFavoriteCharacters().fold(
                onSuccess = { characters ->
                    this@FavouriteCharactersViewModel.characters = characters

                    val display: CharactersDisplay = characters.toDisplay()
                    _charactersDisplay.value = display
                },
                onFailure = { error ->
                    Log.d("RickAndMorty", error.message ?: "Unknown error")
                }
            )
        }
    }

    fun removeCharacterFromFavourites(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            removeToFavouriteUseCase.removeCharacterFromFavorites(id)

            val updatedList = _charactersDisplay.value.characterList.filter { it.id != id }

            _charactersDisplay.value = CharactersDisplay(updatedList)
        }
    }
}