package com.example.data_source_rest.search

import com.example.common.Either
import com.example.data_source_rest.model.CharactersDTO
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchApi: SearchApiRest
) : SearchDataSource {

    override fun getCharacterByName(name: String): Either<Throwable, CharactersDTO> {
        val call = searchApi.getCharacterByName(name)
        val response = call.execute()

        return if (response.isSuccessful) {
            response.body()?.let {
                Either.Success(it)
            } ?: Either.Error(Throwable("Error searching"))

        } else {
            Either.Error(Throwable("Error while searching"))
        }
    }
}