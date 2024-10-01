package com.example.domain.Favourites.remove

interface RemoveToFavouritesRepository {

    suspend fun removeCharacterFromFavorites(id: Int)

}