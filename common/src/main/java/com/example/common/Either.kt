package com.example.common


sealed class Either<out E, out T> {
    data class Success<out T>(val value: T) : Either<Nothing, T>()
    data class Error<out E>(val error: E) : Either<E, Nothing>()


    fun isRight(): Boolean = this is Success

    fun isLeft(): Boolean = this is Error

    fun getRightValue(): T? = when (this) {
        is Success -> value
        else -> null
    }

    fun getLeftValue(): E? = when (this) {
        is Error -> error
        else -> null
    }

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onFailure: (E) -> R
    ): R = when (this) {
        is Success -> onSuccess(value)
        is Error -> onFailure(error)
    }
}