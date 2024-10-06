package com.example.presentation_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Character
import com.example.domain.search.GetSearchUseCase
import com.example.domain.search.GetSearchUseCase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
) : ViewModel() {

    private val _characterList = MutableLiveData<List<Character>>(emptyList())
    val characterList: LiveData<List<Character>> = _characterList

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
                    _characterList.value = characters.characterList
                },
                onFailure = { _ ->
                    _characterList.value = emptyList()
                }
            )
        }
    }
}