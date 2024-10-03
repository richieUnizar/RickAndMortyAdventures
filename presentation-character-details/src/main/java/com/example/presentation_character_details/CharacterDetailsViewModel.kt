package com.example.presentation_character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.character.GetCharacterUseCase
import com.example.domain.character.GetCharacterUseCase.Params
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

    private val _characterDetail = MutableStateFlow(CharacterDisplay(0, "", "", "", "", false))
    val characterDetail: StateFlow<CharacterDisplay> = _characterDetail

    fun fetchCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterUseCase.run(Params(id)).fold(
                onSuccess = { character ->
                    _characterDetail.value = character.toDisplay()
                },
                onFailure = { _ ->
                    _characterDetail.value.copy(hasError = true)
                }
            )
        }
    }
}