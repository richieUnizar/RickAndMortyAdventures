package com.example.data_source_rest.di

import com.example.data_source_rest.characters.CharactersApiRest
import com.example.data_source_rest.characters.CharactersDataSource
import com.example.data_source_rest.characters.CharactersDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceRestModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCharactersApiRest(retrofit: Retrofit): CharactersApiRest {
        return retrofit.create(CharactersApiRest::class.java)
    }

    @Provides
    @Singleton
    fun provideCharactersDataSourceImpl(
        characterApiRest: CharactersApiRest
    ): CharactersDataSource {
        return CharactersDataSourceImpl(characterApiRest)
    }
}
