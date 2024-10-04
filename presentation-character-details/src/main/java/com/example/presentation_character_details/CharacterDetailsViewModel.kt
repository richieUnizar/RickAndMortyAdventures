package com.example.presentation_character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _characterDetail = MutableLiveData<DetailsDisplay>()
    val characterDetail: LiveData<DetailsDisplay> = _characterDetail

    fun fetchCharacters(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterUseCase.run(Params(id)).fold(
                onSuccess = { character ->
                    val display = DetailsDisplay(
                        hasError = false,
                        characterDisplay = character.toDisplay()
                    )
                    _characterDetail.postValue(display)
                },
                onFailure = { _ ->
                    _characterDetail.postValue(DetailsDisplay(hasError = true))
                }
            )
        }
    }
}