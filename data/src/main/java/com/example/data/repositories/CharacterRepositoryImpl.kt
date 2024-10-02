package com.example.data.repositories

import com.example.common.Either
import com.example.data.mapper.toDomain
import com.example.data_source_rest.character.CharacterDataSource
import com.example.data_source_rest.model.CharacterDTO
import com.example.domain.character.CharacterRepository
import com.example.domain.model.Character

class CharacterRepositoryImpl(
    private val characterDataSource: CharacterDataSource
) : CharacterRepository {

    override suspend fun getCharacter(id: Int): Either<Throwable, Character> {
        val result: Either<Throwable, CharacterDTO> = characterDataSource.getCharacterById(id)

        return result.fold(
            onSuccess = { characterDTO ->
                Either.Success(characterDTO.toDomain())
            },
            onFailure = { error ->
                Either.Error(error)
            }
        )
    }

}