package com.example.domain.search

import com.example.common.Either
import com.example.domain.Favourites.get_list.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.model.Characters
import com.example.domain.search.GetSearchUseCase.Params
import com.example.domain.utils.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val favouriteCharactersUseCase: GetFavouriteCharactersUseCase,
) : UseCase<Params, Throwable, Characters> {

    override suspend fun run(params: Params): Either<Throwable, Characters> {
        return try {
            coroutineScope {
                val charactersResult =
                    async { searchRepository.getCharacterByName(params.name) }.await()
                val favouritesResult =
                    async { favouriteCharactersUseCase.run() }.await()

                combineResults(charactersResult, favouritesResult)
            }
        } catch (error: Throwable) {
            Either.Error(error)
        }
    }

    private fun combineResults(
        charactersResult: Either<Throwable, Characters>,
        favouritesResult: Either<Throwable, List<FavouriteCharacter>>
    ): Either<Throwable, Characters> {
        return if (charactersResult.isRight() && favouritesResult.isRight()) {

            val characters = charactersResult.getRightValue()!!
            val favourites = favouritesResult.getRightValue()!!

            val updatedCharacters = updateCharactersWithFavourites(characters, favourites)

            Either.Success(updatedCharacters)
        } else {
            charactersResult
        }
    }

    private fun updateCharactersWithFavourites(
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

    data class Params(
        val name: String
    )
}

