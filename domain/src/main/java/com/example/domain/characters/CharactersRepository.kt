package com.example.domain.characters

import com.example.common.Either
import com.example.domain.model.Characters

interface CharactersRepository {

    suspend fun getCharacters(page: Int): Either<Throwable, Characters>

}