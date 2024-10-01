package com.example.data.repositories

import com.example.common.Either
import com.example.data.mapper.toDomain
import com.example.data_source_rest.character.CharacterDataSource
import com.example.domain.character.CharacterRepository
import com.example.domain.model.Character

class CharacterRepositoryImpl(
    private val characterDataSource: CharacterDataSource
) : CharacterRepository {

    override suspend fun getCharacter(id: Int): Either<Throwable, Character> {
        val result = characterDataSource.getCharacterById(id)

        return if (result.isRight()) {
            val character = result.getRightValue()?.let { characterDTO ->
                characterDTO.toDomain()
            }
            Either.Success(character!!)
        } else {
            val error = result.getLeftValue()!!
            Either.Error(error)
        }
    }

}