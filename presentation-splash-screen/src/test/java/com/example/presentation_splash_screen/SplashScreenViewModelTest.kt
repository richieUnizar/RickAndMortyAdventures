package com.example.presentation_splash_screen

import com.example.common.asFailure
import com.example.common.asSuccess
import com.example.domain.characters.GetCharactersUseCase
import com.example.test_utils_android.InstantExecutorExtension
import com.example.test_utils_android.TestCoroutineExtension
import com.example.test_utils_android.getOrAwaitValue
import com.example.test_utils_android.observeNoValue
import com.example.test_utils_android.test_utils.createCharacters
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URLEncoder

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, TestCoroutineExtension::class)
class SplashScreenViewModelTest {

    private lateinit var viewModel: SplashScreenViewModel

    private val getCharactersUseCase: GetCharactersUseCase = mockk()

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given a successful character fetch When fetchCharacters is called Then characters are loaded and loading is false`() =
        runTest {
            val characters = createCharacters()
            val encodeCharacters: String = Json.encodeToString(characters)
            val expectedEncodingCharacters = URLEncoder.encode(encodeCharacters, "UTF-8")

            coEvery { getCharactersUseCase.run(GetCharactersUseCase.Params(1)) } returns characters.asSuccess()

            viewModel = SplashScreenViewModel(getCharactersUseCase)

            coVerify(exactly = 1) { getCharactersUseCase.run(GetCharactersUseCase.Params(1)) }
            assertFalse(viewModel.loading.getOrAwaitValue())
            assertEquals(expectedEncodingCharacters, viewModel.characters.getOrAwaitValue())
        }

    @Test
    fun `Given a failed character fetch, When fetchCharacters is called, Then loading is set to false and characters is not updated`() =
        runTest {
            coEvery { getCharactersUseCase.run(GetCharactersUseCase.Params(1)) } returns Throwable("Error").asFailure()

            viewModel = SplashScreenViewModel(getCharactersUseCase)

            coVerify(exactly = 1) { getCharactersUseCase.run(GetCharactersUseCase.Params(1)) }
            assertFalse(viewModel.loading.getOrAwaitValue())

            viewModel.characters.observeNoValue()
        }

}
