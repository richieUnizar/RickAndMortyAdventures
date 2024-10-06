package com.example.domain.base

sealed class BaseError (override val message: String) : Throwable(message)