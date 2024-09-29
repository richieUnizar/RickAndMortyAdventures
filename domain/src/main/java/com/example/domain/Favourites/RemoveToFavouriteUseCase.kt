package com.example.domain.Favourites

import javax.inject.Inject

class RemoveToFavouriteUseCase @Inject constructor(
    private val removeToFavouritesRepository: RemoveToFavouritesRepository
) {

    suspend fun removeCharacterFromFavorites(id: Int) {
        removeToFavouritesRepository.removeCharacterFromFavorites(id)
    }

}