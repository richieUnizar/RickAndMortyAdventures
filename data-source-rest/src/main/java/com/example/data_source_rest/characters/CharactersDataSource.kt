package com.example.data_source_rest.characters

import com.example.common.Either
import com.example.data_source_rest.model.CharactersDTO

interface CharactersDataSource {

    suspend fun getCharacters(page: Int) : Either<Throwable, CharactersDTO>
}