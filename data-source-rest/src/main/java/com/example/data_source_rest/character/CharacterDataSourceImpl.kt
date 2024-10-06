package com.example.data_source_rest.character

import com.example.common.Either
import com.example.data_source_rest.base.BaseDataSource
import com.example.data_source_rest.model.CharacterDTO
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CharacterDataSourceImpl @Inject constructor(
    private val characterApiRest: CharacterApiRest
) : BaseDataSource(), CharacterDataSource {

    override suspend fun getCharacterById(id: Int): Either<Throwable, CharacterDTO> {
        val call = characterApiRest.getCharacterById(id)
        return safeApiCall(call)
    }


}