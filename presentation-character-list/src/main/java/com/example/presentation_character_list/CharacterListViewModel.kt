package com.example.presentation_character_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.characters.GetCharactersUseCase.Params
import com.example.presentation_base.FavouriteManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    private val favouriteManager: FavouriteManager
) : ViewModel() {

    private val _characters =
        MutableLiveData(CharactersDisplay(0, emptyList(), true, false))
    val characters: LiveData<CharactersDisplay> = _characters

    private var pageToLoad = 1
    private var numberOfPages: Int? = null
    private var charactersList: List<Character>? = null

    fun intiPage(charactersJson: String?) {
        if (charactersJson != null) {
            val decodedCharactersJson = URLDecoder.decode(charactersJson, "UTF-8")
            val characters = Json.decodeFromString<Characters>(decodedCharactersJson)
            _characters.value = characters.toDisplay()
        } else {
            fetchCharacters(pageToLoad)
        }
    }

    fun loadNextPage(index: Int) {
        numberOfPages?.let {
            val isNearEndOfList = index == (_characters.value?.characterList?.count() ?: 0) - 10
            val shouldLoadNextPage = pageToLoad <= it

            if (isNearEndOfList && shouldLoadNextPage) {
                fetchCharacters(pageToLoad)
            }
        }
    }

    private fun fetchCharacters(page: Int) {
        _characters.value = _characters.value?.copy(loading = true)

        viewModelScope.launch {
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

        val updatedCharacterList = _characters.value?.characterList?.let { currentList ->
            currentList + display.characterList
        } ?: display.characterList

        _characters.value = display.copy(
            characterList = updatedCharacterList,
            loading = false,
            hasError = false
        )
    }

    private fun errorToFetchCharacters() {
        _characters.value = _characters.value?.copy(hasError = true)
    }

    fun onHeartIconClicked(characterDisplay: CharacterDisplay, isHeartSelected: Boolean) {

        updateCharacterFavourites(characterDisplay, isHeartSelected)

        val favouriteCharacter = characterDisplay.mapToFavouriteCharacter()

        viewModelScope.launch {
            favouriteManager.handleHeartIconClick(favouriteCharacter, isHeartSelected)
        }
    }

    private fun updateCharacterFavourites(
        characterDisplay: CharacterDisplay,
        isHeartSelected: Boolean
    ) {
        updateCharacterList(characterDisplay.id) { character ->
            character.copy(isInFavourites = isHeartSelected)
        }
    }

    fun heartIconDetailChanged(id: Int) {
        updateCharacterList(id) { character ->
            character.copy(isInFavourites = !character.isInFavourites)
        }
    }

    private fun updateCharacterList(id: Int, update: (CharacterDisplay) -> CharacterDisplay) {
        val updatedCharacterList = _characters.value?.characterList?.map { character ->
            if (character.id == id) {
                update(character)
            } else {
                character
            }
        }
        updatedCharacterList?.let {
            _characters.value = _characters.value?.copy(
                characterList = updatedCharacterList
            )
        }
    }

}