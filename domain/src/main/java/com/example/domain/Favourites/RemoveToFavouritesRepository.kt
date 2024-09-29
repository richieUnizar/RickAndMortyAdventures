package com.example.domain.Favourites

interface RemoveToFavouritesRepository {

    suspend fun removeCharacterFromFavorites(id: Int)

}