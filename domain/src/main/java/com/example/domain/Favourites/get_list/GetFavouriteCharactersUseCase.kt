package com.example.domain.Favourites.get_list

import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.utils.NoParamsUseCase
import javax.inject.Inject

class GetFavouriteCharactersUseCase @Inject constructor(
    private val favouriteCharactersRepository: FavouriteCharactersRepository
) : NoParamsUseCase<Throwable, List<FavouriteCharacter>>{

    override suspend fun run() = favouriteCharactersRepository.getFavoriteCharacters()

}