package com.example.domain.utils

import com.example.common.Either

interface UseCase<in P, out R, out E> {
    suspend fun run(params: P): Either<R, E>
}

interface NoParamsUseCase<out R, out E> {
    suspend fun run(): Either<R, E>
}