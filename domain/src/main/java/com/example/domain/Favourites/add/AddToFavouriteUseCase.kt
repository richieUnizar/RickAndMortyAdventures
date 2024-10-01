package com.example.domain.Favourites.add

import javax.inject.Inject
import com.example.domain.model.Character

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