package com.example.data.repositories

import com.example.common.Either
import com.example.data_source_room.dao.CharacterDao
import com.example.data_source_room.data.CharacterEntity
import com.example.domain.Favourites.FavouriteCharactersRepository
import com.example.domain.characters.Character
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavouriteCharactersRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : FavouriteCharactersRepository {

    override suspend fun getFavoriteCharacters(): Either<Throwable, List<Character>> {
        return try {
            val favoriteCharacters = characterDao.getFavoriteCharacters().first()

            val characters = favoriteCharacters.map { entity ->
                Character(
                    id = entity.id,
                    name = entity.name,
                    status = entity.status,
                    species = entity.species,
                    image = entity.image
                )
            }
            Either.Success(characters)
        } catch (e: Throwable) {
            Either.Error(e)
        }
    }
}