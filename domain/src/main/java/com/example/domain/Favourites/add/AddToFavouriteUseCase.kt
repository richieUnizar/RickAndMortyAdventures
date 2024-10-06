package com.example.domain.Favourites.add

import com.example.domain.Favourites.model.FavouriteCharacter
import javax.inject.Inject
import com.example.domain.model.Character

class AddToFavouriteUseCase @Inject constructor(
    private val addToFavouritesRepository: AddToFavouritesRepository
) {

    suspend fun addCharacterToFavorites(params: Params) {
        addToFavouritesRepository.addCharacterToFavorites(params.favouriteCharacter)
    }

    data class Params(
        var favouriteCharacter: FavouriteCharacter
    )

}