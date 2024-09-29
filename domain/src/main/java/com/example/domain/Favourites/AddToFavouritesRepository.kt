package com.example.domain.Favourites

import com.example.domain.characters.Character

interface AddToFavouritesRepository {

    suspend fun addCharacterToFavorites(character: Character)
}