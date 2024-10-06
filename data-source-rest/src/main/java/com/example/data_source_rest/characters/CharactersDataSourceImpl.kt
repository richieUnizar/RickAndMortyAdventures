package com.example.data_source_rest.characters

import com.example.common.Either
import com.example.data_source_rest.base.BaseDataSource
import com.example.data_source_rest.model.CharactersDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CharactersDataSourceImpl @Inject constructor(
    private val characterApiRest: CharactersApiRest
) : BaseDataSource(), CharactersDataSource {

    override suspend fun getCharacters(page: Int): Either<Throwable, CharactersDTO> {
        val call = characterApiRest.getCharacters(page)
        return safeApiCall(call)
    }

}