package com.example.data_source_rest.search

import com.example.common.Either
import com.example.data_source_rest.model.CharactersDTO

interface SearchDataSource {

    suspend fun getCharacterByName(name: String): Either<Throwable, CharactersDTO>

}