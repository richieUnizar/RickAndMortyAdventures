package com.example.data.di

import com.example.data.repositories.CharactersRepositoryImpl
import com.example.data_source_rest.characters.CharactersDataSource
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
    fun provideCharactersRepository(
        charactersDataSource: CharactersDataSource
    ): CharactersRepository {
        return CharactersRepositoryImpl(charactersDataSource)
    }

}