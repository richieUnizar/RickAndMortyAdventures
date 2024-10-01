package com.example.data.repositories

import com.example.common.Either
import com.example.data.mapper.toDomain
import com.example.data_source_room.dao.CharacterDao
import com.example.domain.Favourites.get_list.FavouriteCharactersRepository
import com.example.domain.Favourites.model.FavouriteCharacter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavouriteCharactersRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : FavouriteCharactersRepository {

    override suspend fun getFavoriteCharacters(): Either<Throwable, List<FavouriteCharacter>> {
        return try {
            val favoriteCharacters = characterDao.getFavoriteCharacters().first()
            val characters = favoriteCharacters.toDomain()
            Either.Success(characters)
        } catch (e: Throwable) {
            Either.Error(e)
        }
    }
}