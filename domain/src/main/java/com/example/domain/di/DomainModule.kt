package com.example.domain.di

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
}