package com.example.domain.Favourites.get_list

import javax.inject.Inject

class GetFavouriteCharactersUseCase @Inject constructor(
    private val favouriteCharactersRepository: FavouriteCharactersRepository
) {

    suspend fun getFavoriteCharacters() = favouriteCharactersRepository.getFavoriteCharacters()

}