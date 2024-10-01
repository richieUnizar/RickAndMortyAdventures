package com.example.domain.Favourites.add

import com.example.domain.model.Character

interface AddToFavouritesRepository {

    suspend fun addCharacterToFavorites(character: Character)
}