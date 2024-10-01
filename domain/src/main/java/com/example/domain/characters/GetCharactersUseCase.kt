package com.example.domain.characters

import com.example.common.Either
import com.example.domain.model.Characters
import javax.inject.Inject


class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharactersRepository,
) {

    suspend fun getCharacters(page: Int): Either<Throwable, Characters> = characterRepository.getCharacters(page)
}