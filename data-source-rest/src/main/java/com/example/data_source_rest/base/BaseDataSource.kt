package com.example.data_source_rest.base

import com.example.common.Either
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

abstract class BaseDataSource {

    protected suspend fun <T> safeApiCall(call: Call<T>): Either<Throwable, T> {
        return suspendCancellableCoroutine { continuation ->
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful && response.body() != null) {
                        continuation.resume(Either.Success(response.body()!!))
                    } else {
                        continuation.resume(Either.Error(Throwable("Error: ${response.message()}")))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })

            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}
