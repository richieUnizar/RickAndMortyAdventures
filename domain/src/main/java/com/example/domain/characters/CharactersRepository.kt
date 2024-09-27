package com.example.domain.characters

import com.example.common.Either

interface CharactersRepository {

    suspend fun getCharacters(page: Int): Either<Throwable, Characters>

}