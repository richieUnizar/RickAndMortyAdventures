package com.example.test_utils_android

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class TestCoroutineExtension(
    private val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
) : BeforeAllCallback,
    AfterAllCallback {

    override fun beforeAll(context: ExtensionContext?) {
        try {
            Dispatchers.setMain(testDispatcher)
        } catch (_: Exception) {
            /* no-op */
        }
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}