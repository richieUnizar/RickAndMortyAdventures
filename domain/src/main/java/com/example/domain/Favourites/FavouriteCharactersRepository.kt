package com.example.domain.Favourites

import com.example.common.Either
import com.example.domain.characters.Character

interface FavouriteCharactersRepository {

    suspend fun getFavoriteCharacters(): Either<Throwable, List<Character>>

}