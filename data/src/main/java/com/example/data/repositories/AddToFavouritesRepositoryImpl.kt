package com.example.data.repositories

import com.example.data_source_room.dao.CharacterDao
import com.example.data_source_room.data.CharacterEntity
import com.example.domain.Favourites.add.AddToFavouritesRepository
import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.model.Character
import javax.inject.Inject

class AddToFavouritesRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : AddToFavouritesRepository {

    override suspend fun addCharacterToFavorites(favouriteCharacter: FavouriteCharacter) {
        with(favouriteCharacter){
            val entity = CharacterEntity(
                id = id,
                name = name,
                status = status,
                species = species,
                image = image
            )
            characterDao.insertCharacter(entity)
        }
    }

}