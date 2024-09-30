package com.example.domain.search

import com.example.common.Either
import com.example.domain.characters.Characters

interface SearchRepository {

    fun getCharacterByName(name: String): Either<Throwable, Characters>

}