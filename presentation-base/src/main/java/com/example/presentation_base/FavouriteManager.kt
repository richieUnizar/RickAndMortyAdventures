package com.example.presentation_base

import com.example.domain.Favourites.add.AddToFavouriteUseCase
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import javax.inject.Inject

class FavouriteManager @Inject constructor(
    private val favouriteCharacterUseCase: AddToFavouriteUseCase,
    private val removeToFavouriteUseCase: RemoveToFavouriteUseCase
) {

    suspend fun handleHeartIconClick(
        favouriteCharacter: FavouriteCharacter,
        isHeartSelected: Boolean,
    ) {
        if (isHeartSelected) {
            favouriteCharacterUseCase.addCharacterToFavorites(
                AddToFavouriteUseCase.Params(
                    favouriteCharacter
                )
            )
        } else {
            removeToFavouriteUseCase.removeCharacterFromFavorites(favouriteCharacter.id)
        }
    }

}