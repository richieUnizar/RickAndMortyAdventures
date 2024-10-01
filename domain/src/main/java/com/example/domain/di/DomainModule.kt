package com.example.domain.di

import com.example.domain.Favourites.get_list.FavouriteCharactersRepository
import com.example.domain.Favourites.add.AddToFavouriteUseCase
import com.example.domain.Favourites.add.AddToFavouritesRepository
import com.example.domain.Favourites.get_list.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import com.example.domain.Favourites.remove.RemoveToFavouritesRepository
import com.example.domain.character.CharacterRepository
import com.example.domain.character.GetCharacterUseCase
import com.example.domain.characters.CharactersRepository
import com.example.domain.characters.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun characterUseCase(characterRepository: CharacterRepository): GetCharacterUseCase {
        return GetCharacterUseCase(characterRepository)
    }

    @Provides
    @Singleton
    fun charactersUseCase(charactersRepository: CharactersRepository): GetCharactersUseCase {
        return GetCharactersUseCase(charactersRepository)
    }

    @Provides
    @Singleton
    fun addToFavouritesUseCase(addToFavouritesRepository: AddToFavouritesRepository): AddToFavouriteUseCase {
        return AddToFavouriteUseCase(addToFavouritesRepository)
    }

    @Provides
    @Singleton
    fun removeToFavouritesUseCase(removeToFavouritesRepository: RemoveToFavouritesRepository): RemoveToFavouriteUseCase {
        return RemoveToFavouriteUseCase(removeToFavouritesRepository)
    }

    @Provides
    @Singleton
    fun getFavouritesCharactersUseCase(favouriteCharactersRepository: FavouriteCharactersRepository): GetFavouriteCharactersUseCase {
        return GetFavouriteCharactersUseCase(favouriteCharactersRepository)
    }
}