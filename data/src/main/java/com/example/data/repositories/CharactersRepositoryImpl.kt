package com.example.data.repositories

import com.example.common.Either
import com.example.data.mapper.toDomain
import com.example.data_source_rest.characters.CharactersDataSource
import com.example.domain.Favourites.get_list.FavouriteCharactersRepository
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.model.Characters
import com.example.domain.characters.CharactersRepository

class CharactersRepositoryImpl(
    private val charactersDataSource: CharactersDataSource,
) : CharactersRepository {

    override suspend fun getCharacters(page: Int): Either<Throwable, Characters> {
        val result = charactersDataSource.getCharacters(page)

        return if (result.isRight()) {
            val characters = result.getRightValue()!!.let { charactersDTO ->
                charactersDTO.toDomain()
            }

            Either.Success(characters)
        } else {
            val error = result.getLeftValue()!!
            Either.Error(error)
        }
    }

}