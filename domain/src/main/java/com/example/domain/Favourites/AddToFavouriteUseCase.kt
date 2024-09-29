package com.example.domain.Favourites

import javax.inject.Inject
import com.example.domain.characters.Character

class AddToFavouriteUseCase @Inject constructor(
    private val addToFavouritesRepository: AddToFavouritesRepository
) {

    suspend fun addCharacterToFavorites(params: Params) {
        addToFavouritesRepository.addCharacterToFavorites(params.character)
    }

    data class Params(
        var character: Character
    )

}