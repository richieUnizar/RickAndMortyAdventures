package com.example.presentation_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.domain.search.GetSearchUseCase
import com.example.domain.search.GetSearchUseCase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
) : ViewModel() {

    private val _characterList = MutableLiveData<String>()
    val characterList: LiveData<String> = _characterList

    private val _familyMembers = MutableLiveData<List<String>>(emptyList())
    val familyMembers: LiveData<List<String>> = _familyMembers

    init {
        _familyMembers.value =
            listOf("Rick Sanchez", "Morty Smith", "Beth Smith", "Jerry Smith", "Summer Smith")
    }

    fun onSearchClick(name: String) {
        viewModelScope.launch {
            getSearchUseCase.run(Params(name)).fold(
                onSuccess = { characters ->
                    updateCharactersStatus(characters)

                },
                onFailure = { _ ->
                    val characters = Characters(Info(0, 0), emptyList())
                    updateCharactersStatus(characters)
                }
            )
        }
    }

    private fun updateCharactersStatus(characters: Characters) {
        val encodeCharacters: String = Json.encodeToString(characters)
        val urlEncodeCharacters = URLEncoder.encode(encodeCharacters, "UTF-8")
        _characterList.value = urlEncodeCharacters
    }
}