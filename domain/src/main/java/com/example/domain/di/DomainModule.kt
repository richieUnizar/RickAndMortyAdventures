package com.example.domain.di

import com.example.domain.Favourites.FavouriteCharactersRepository
import com.example.domain.Favourites.AddToFavouriteUseCase
import com.example.domain.Favourites.AddToFavouritesRepository
import com.example.domain.Favourites.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.RemoveToFavouriteUseCase
import com.example.domain.Favourites.RemoveToFavouritesRepository
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