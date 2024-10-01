package com.example.presentation_character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Favourites.add.AddToFavouriteUseCase
import com.example.domain.Favourites.add.AddToFavouriteUseCase.*
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import com.example.domain.model.Character
import com.example.domain.model.Characters
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

    private val _characterList = MutableStateFlow(CharactersDisplay(0, emptyList()))
    val characterList: StateFlow<CharactersDisplay> = _characterList

    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    private var pageToLoad = 1
    private var numberOfPages: Int? = null
    private var charactersList: List<Character>? = null

    init {
        fetchCharacters(pageToLoad)
    }

    fun loadNextPage(index: Int) {
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
                    _hasError.value = false
                    val cha: Characters = characters
                    pageToLoad++
                    numberOfPages = characters.info.numberOfPages
                    this@CharacterListViewModel.charactersList = characters.characterList

                    val display: CharactersDisplay = characters.toDisplay()

                    val updatedCharacterList =
                        _characterList.value.characterList + display.characterList

                    _characterList.value = display.copy(
                        characterList = updatedCharacterList
                    )
                },
                onFailure = { _ ->
                    _hasError.value = true
                }
            )
        }
    }

    fun onHeartIconClicked(isHeartSelected: Boolean, characterDisplay: CharacterDisplay) {
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

    fun updateCharacterList(characters: List<Character>) {
        val display = characters.toDisplay()

        _characterList.value = _characterList.value.copy(
            numberOfCharacters = display.count(),
            characterList = display
        )
    }
}