package com.example.data.repositories

import com.example.common.Either
import com.example.data_source_room.dao.CharacterDao
import com.example.data_source_room.data.CharacterEntity
import com.example.domain.Favourites.FavouriteCharactersRepository
import com.example.domain.Favourites.RemoveToFavouritesRepository
import com.example.domain.characters.Character
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveToFavouritesRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : RemoveToFavouritesRepository {

    override suspend fun removeCharacterFromFavorites(id: Int) {
        characterDao.deleteCharacter(id)
    }
}