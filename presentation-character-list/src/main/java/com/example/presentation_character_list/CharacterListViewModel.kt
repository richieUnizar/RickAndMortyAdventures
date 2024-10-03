package com.example.presentation_character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Favourites.add.AddToFavouriteUseCase
import com.example.domain.Favourites.add.AddToFavouriteUseCase.*
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.characters.GetCharactersUseCase.Params
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

    private val _characterList =
        MutableStateFlow(CharactersDisplay(0, emptyList(), true, false))
    val characterList: StateFlow<CharactersDisplay> = _characterList

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

        _characterList.value = _characterList.value.copy(loading = true)

        viewModelScope.launch(Dispatchers.IO) {
            charactersUseCase.run(Params(page)).fold(
                onSuccess = { characters ->
                    successToFetchCharacters(characters)
                },
                onFailure = { _ ->
                    errorToFetchCharacters()
                }
            )
        }
    }

    private fun successToFetchCharacters(characters: Characters) {
        pageToLoad++
        numberOfPages = characters.info.numberOfPages
        this.charactersList = characters.characterList

        val display: CharactersDisplay = characters.toDisplay()

        val updatedCharacterList =
            _characterList.value.characterList + display.characterList

        _characterList.value = display.copy(
            characterList = updatedCharacterList,
            loading = false,
            hasError = false
        )
    }

    private fun errorToFetchCharacters() {
        _characterList.value = _characterList.value.copy(
            hasError = true
        )
    }

    fun onHeartIconClicked(isHeartSelected: Boolean, characterDisplay: CharacterDisplay) {
        viewModelScope.launch(Dispatchers.IO) {

            updateCharacterFavourites(characterDisplay, isHeartSelected)

            if (isHeartSelected) {
                charactersList?.find { it.id == characterDisplay.id }?.let { safeCharacter ->
                    favouriteCharacterUseCase.addCharacterToFavorites(Params(safeCharacter))
                }
            } else {
                removeToFavouriteUseCase.removeCharacterFromFavorites(characterDisplay.id)
            }
        }
    }

    private fun updateCharacterFavourites(
        characterDisplay: CharacterDisplay,
        isHeartSelected: Boolean
    ) {
        val updatedCharacterList = _characterList.value.characterList.map { character ->
            if (character.id == characterDisplay.id) {
                character.copy(isInFavourites = isHeartSelected)
            } else {
                character
            }
        }

        _characterList.value = _characterList.value.copy(
            characterList = updatedCharacterList
        )
    }


    fun updateCharacterList(characters: List<Character>) {
        charactersList = characters

        _characterList.value = _characterList.value.copy(
            numberOfCharacters = characters.count(),
            characterList = characters.toDisplay(),
            loading = false,
        )
    }

}