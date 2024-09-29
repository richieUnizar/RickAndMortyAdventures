package com.example.data.repositories

import com.example.common.Either
import com.example.data.toDomain
import com.example.data_source_rest.characters.CharactersDataSource
import com.example.domain.Favourites.FavouriteCharactersRepository
import com.example.domain.characters.Character
import com.example.domain.characters.Characters
import com.example.domain.characters.CharactersRepository

class CharactersRepositoryImpl(
    private val charactersDataSource: CharactersDataSource,
    private val favouriteCharactersUseCase: FavouriteCharactersRepository,
) : CharactersRepository {

    override suspend fun getCharacters(page: Int): Either<Throwable, Characters> {
        val result = charactersDataSource.getCharacters(page)

        return if (result.isRight()) {
            val favourites = favouriteCharactersUseCase.getFavoriteCharacters().getRightValue()
            val characters = result.getRightValue()!!.let { charactersDTO ->
                charactersDTO.toDomain()
            }
            val charactersWithFavourites = favourites?.let {
                getCharacterWithFavourites(characters, favourites)
            } ?: characters

            Either.Success(charactersWithFavourites)
        } else {
            val error = result.getLeftValue()!!
            Either.Error(error)
        }
    }

    private fun getCharacterWithFavourites(
        characters: Characters,
        favourites: List<Character>
    ): Characters {
        return characters.copy(
            characterList = characters.characterList.map { safeCharacter ->
                safeCharacter.copy(
                    isInFavourites = favourites.any { it.id == safeCharacter.id }
                )
            }
        )
    }

}