package com.example.domain.character

import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend fun getCharacter(id: Int) = characterRepository.getCharacter(id)

}