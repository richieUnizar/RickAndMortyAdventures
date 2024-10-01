package com.example.data.repositories

import com.example.common.Either
import com.example.data.mapper.toDomain
import com.example.data_source_rest.search.SearchDataSource
import com.example.domain.model.Characters
import com.example.domain.search.SearchRepository

class SearchRepositoryImpl(
    private val searchDataSource: SearchDataSource,
) : SearchRepository {

    override suspend fun getCharacterByName(name: String): Either<Throwable, Characters> {
        val result = searchDataSource.getCharacterByName(name)

        return if (result.isRight()) {
            val characters = result.getRightValue()!!.let { charactersDTO ->
                charactersDTO.toDomain()
            }
            Either.Success(characters)
        } else {
            val error = result.getLeftValue()!!
            Either.Error(error)
        }
    }

}