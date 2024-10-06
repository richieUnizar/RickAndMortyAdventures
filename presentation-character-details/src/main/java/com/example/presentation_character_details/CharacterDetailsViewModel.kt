package com.example.presentation_character_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.character.GetCharacterUseCase
import com.example.domain.character.GetCharacterUseCase.Params
import com.example.domain.model.Character
import com.example.presentation_base.FavouriteManager
import com.example.presentation_base.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterUseCase: GetCharacterUseCase,
    private val favouriteManager: FavouriteManager,
) : ViewModel() {

    private val _characterDetail = MutableLiveData<DetailsDisplay>()
    val characterDetail: LiveData<DetailsDisplay> = _characterDetail

    fun fetchCharacters(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            characterUseCase.run(Params(id)).fold(
                onSuccess = { character ->
                    val display = DetailsDisplay(
                        hasError = false,
                        characterDisplay = character.toDisplay(isFavorite)
                    )
                    _characterDetail.value = display
                },
                onFailure = { _ ->
                    _characterDetail.value =DetailsDisplay(hasError = true)
                }
            )
        }
    }

    fun onHeartIconClicked(isFavorite: Boolean) {
        val favouriteCharacter = _characterDetail.value?.characterDisplay?.toFavouriteCharacter()

        favouriteCharacter?.let {
            viewModelScope.launch {
                favouriteManager.handleHeartIconClick(it, isFavorite)
            }
        }
    }
}
