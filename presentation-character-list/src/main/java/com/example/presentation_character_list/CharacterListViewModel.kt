package com.example.presentation_character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Favourites.AddToFavouriteUseCase
import com.example.domain.Favourites.AddToFavouriteUseCase.*
import com.example.domain.Favourites.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.RemoveToFavouriteUseCase
import com.example.domain.characters.Character
import com.example.domain.characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    private val favouriteCharacterUseCase: AddToFavouriteUseCase,
    private val removeToFavouriteUseCase: RemoveToFavouriteUseCase,
) : ViewModel() {

    private val _characterList = MutableStateFlow(CharactersDisplay(emptyList()))
    val characterList: StateFlow<CharactersDisplay> = _characterList

    private var pageToLoad = 1
    private var numberOfPages: Int? = null
    private var charactersList: List<Character>? = null

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
                    this@CharacterListViewModel.charactersList = characters.characterList

                    val display: CharactersDisplay = characters.toDisplay()
                    _characterList.value = display
                },
                onFailure = { error ->
                    Log.d("RickAndMorty", error.message ?: "Unknown error")
                }
            )
        }
    }

    fun onHeartIconClicked(isHeartSelected: Boolean, characterDisplay: CharacterDisplay){
        viewModelScope.launch(Dispatchers.IO) {
            if (isHeartSelected) {
                charactersList?.find { it.id == characterDisplay.id }?.let { safeCharacter ->
                    favouriteCharacterUseCase.addCharacterToFavorites(Params(safeCharacter))
                }
            } else {
                removeToFavouriteUseCase.removeCharacterFromFavorites(characterDisplay.id)
            }
        }
    }
}