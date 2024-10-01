package com.example.domain.character

import com.example.common.Either
import com.example.domain.model.Character

interface CharacterRepository {

    suspend fun getCharacter(id: Int): Either<Throwable, Character>

}