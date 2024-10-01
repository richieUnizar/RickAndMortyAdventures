package com.example.data.repositories

import com.example.common.Either
import com.example.data.mapper.toDomain
import com.example.data_source_rest.search.SearchDataSource
import com.example.domain.Favourites.get_list.FavouriteCharactersRepository
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.model.Characters
import com.example.domain.search.SearchRepository

class SearchRepositoryImpl(
    private val searchDataSource: SearchDataSource,
    private val favouriteCharactersUseCase: FavouriteCharactersRepository,
    ) : SearchRepository {

    override suspend fun getCharacterByName(name: String): Either<Throwable, Characters> {
        val favourites = favouriteCharactersUseCase.getFavoriteCharacters().getRightValue()
        val result = searchDataSource.getCharacterByName(name)

        return if (result.isRight()) {
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
        favourites: List<FavouriteCharacter>
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