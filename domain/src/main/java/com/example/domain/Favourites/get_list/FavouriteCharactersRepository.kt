package com.example.domain.Favourites.get_list

import com.example.common.Either
import com.example.domain.Favourites.model.FavouriteCharacter

interface FavouriteCharactersRepository {

    suspend fun getFavoriteCharacters(): Either<Throwable, List<FavouriteCharacter>>

}