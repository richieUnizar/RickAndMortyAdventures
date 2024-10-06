package com.example.data_source_rest.search

import com.example.common.Either
import com.example.data_source_rest.base.BaseDataSource
import com.example.data_source_rest.model.CharactersDTO
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SearchDataSourceImpl @Inject constructor(
    private val searchApi: SearchApiRest
) : BaseDataSource(), SearchDataSource {

    override suspend fun getCharacterByName(name: String): Either<Throwable, CharactersDTO> {
        val call = searchApi.getCharacterByName(name)
        return safeApiCall(call)
    }
}