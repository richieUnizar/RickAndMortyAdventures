package com.example.domain.search

import com.example.common.Either
import com.example.domain.model.Characters

interface SearchRepository {

    suspend fun getCharacterByName(name: String): Either<Throwable, Characters>

}