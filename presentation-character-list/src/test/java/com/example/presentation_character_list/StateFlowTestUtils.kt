package com.example.presentation_character_list

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

suspend fun <T> StateFlow<T>.getOrAwaitValue(predicate: (T) -> Boolean = { true }): T {
    return this.first { predicate(it) }
}