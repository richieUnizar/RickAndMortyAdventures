package com.example.domain.Favourites.add

import com.example.domain.Favourites.model.FavouriteCharacter

interface AddToFavouritesRepository {

    suspend fun addCharacterToFavorites(favouriteCharacter: FavouriteCharacter)
}