package com.example.domain.character

import com.example.common.Either
import com.example.domain.characters.Character
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend fun getCharacter(id: Int): Either<Throwable, Character> = characterRepository.getCharacter(id)

}