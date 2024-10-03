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

    private val _characterDetail = MutableStateFlow(CharacterDisplay(0, "", "", "", ""))
    val characterDetail: StateFlow<CharacterDisplay> = _characterDetail

    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    fun fetchCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterUseCase.run(Params(id)).fold(
                onSuccess = { character ->
                    _hasError.value = false
                    _characterDetail.value = character.toDisplay()
                },
                onFailure = { error ->
                    _hasError.value = true
                }
            )
        }
    }
}