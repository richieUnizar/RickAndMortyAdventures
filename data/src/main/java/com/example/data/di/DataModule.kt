package com.example.data.di

import com.example.data.repositories.AddToFavouritesRepositoryImpl
import com.example.data.repositories.CharacterRepositoryImpl
import com.example.data.repositories.CharactersRepositoryImpl
import com.example.data.repositories.FavouriteCharactersRepositoryImpl
import com.example.data.repositories.RemoveToFavouritesRepositoryImpl
import com.example.data_source_rest.character.CharacterDataSource
import com.example.data_source_rest.characters.CharactersDataSource
import com.example.data_source_room.dao.CharacterDao
import com.example.domain.Favourites.AddToFavouritesRepository
import com.example.domain.Favourites.FavouriteCharactersRepository
import com.example.domain.Favourites.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.RemoveToFavouritesRepository
import com.example.domain.character.CharacterRepository
import com.example.domain.characters.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterDataSource: CharacterDataSource
    ): CharacterRepository {
        return CharacterRepositoryImpl(characterDataSource)
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(
        charactersDataSource: CharactersDataSource,
        favouriteCharactersUseCase: FavouriteCharactersRepository,
    ): CharactersRepository {
        return CharactersRepositoryImpl(charactersDataSource, favouriteCharactersUseCase)
    }

    @Provides
    @Singleton
    fun provideAddToFavouritesRepository(
        characterDao: CharacterDao
    ): AddToFavouritesRepository {
        return AddToFavouritesRepositoryImpl(characterDao)
    }

    @Provides
    @Singleton
    fun provideRemoveToFavouritesRepository(
        characterDao: CharacterDao
    ): RemoveToFavouritesRepository {
        return RemoveToFavouritesRepositoryImpl(characterDao)
    }

    @Provides
    @Singleton
    fun provideFavouriteCharactersRepository(
        characterDao: CharacterDao
    ): FavouriteCharactersRepository {
        return FavouriteCharactersRepositoryImpl(characterDao)
    }

}