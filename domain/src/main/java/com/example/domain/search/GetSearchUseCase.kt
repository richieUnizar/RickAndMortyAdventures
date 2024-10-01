package com.example.domain.search

import javax.inject.Inject

class GetSearchUseCase@Inject constructor(
    private val searchRepository: SearchRepository,
) {

    suspend fun searchByName(named: String) = searchRepository.getCharacterByName(named)
}