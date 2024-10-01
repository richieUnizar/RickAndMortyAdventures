package com.example.data.repositories

import com.example.data_source_room.dao.CharacterDao
import com.example.domain.Favourites.remove.RemoveToFavouritesRepository
import javax.inject.Inject

class RemoveToFavouritesRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : RemoveToFavouritesRepository {

    override suspend fun removeCharacterFromFavorites(id: Int) {
        characterDao.deleteCharacter(id)
    }
}