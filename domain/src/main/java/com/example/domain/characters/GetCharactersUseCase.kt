package com.example.domain.characters

import com.example.common.Either
import com.example.domain.Favourites.get_list.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.characters.GetCharactersUseCase.Params
import com.example.domain.model.Characters
import com.example.domain.utils.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharactersRepository,
    private val favouriteCharactersUseCase: GetFavouriteCharactersUseCase,
) : UseCase<Params, Throwable, Characters> {

    override suspend fun run(params: Params): Either<Throwable, Characters> {
        return try {
            coroutineScope {
                val charactersResult =
                    async { characterRepository.getCharacters(params.page) }.await()
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
        val page: Int
    )
}