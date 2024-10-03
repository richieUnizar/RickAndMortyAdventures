package com.example.domain.character

import com.example.domain.character.GetCharacterUseCase.*
import com.example.domain.model.Character
import com.example.domain.utils.UseCase
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : UseCase<Params, Throwable, Character> {

    override suspend fun run(params: Params) = characterRepository.getCharacter(params.id)

    data class Params(
        val id: Int
    )
}