package com.example.presentation_splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.characters.GetCharactersUseCase.Params
import com.example.domain.model.Characters
import com.example.domain.model.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    private val _characters = MutableLiveData<String>()
    val characters: LiveData<String> = _characters

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        _loading.value = true
        fetchCharacters()
    }

    private fun fetchCharacters() {

        val firstPage = 1

        viewModelScope.launch {
            charactersUseCase.run(Params(firstPage)).fold(
                onSuccess = { characters ->
                    _loading.value = false
                    val encodeCharacters: String = Json.encodeToString(characters)
                    val encodedCharacters1 = URLEncoder.encode(encodeCharacters, "UTF-8")
                    _characters.value = encodedCharacters1
                },
                onFailure = { _ ->
                    _loading.value = false
                }
            )
        }
    }
}