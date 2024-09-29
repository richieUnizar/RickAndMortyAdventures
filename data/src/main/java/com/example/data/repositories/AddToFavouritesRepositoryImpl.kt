package com.example.data.repositories

import com.example.data_source_room.dao.CharacterDao
import com.example.data_source_room.data.CharacterEntity
import com.example.domain.Favourites.AddToFavouritesRepository
import com.example.domain.characters.Character
import javax.inject.Inject

class AddToFavouritesRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : AddToFavouritesRepository {

    override suspend fun addCharacterToFavorites(character: Character) {
        with(character){
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