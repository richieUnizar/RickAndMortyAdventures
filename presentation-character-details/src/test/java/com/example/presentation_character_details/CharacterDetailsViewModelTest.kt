package com.example.presentation_character_details

import com.example.common.Either
import com.example.domain.character.GetCharacterUseCase
import com.example.domain.character.GetCharacterUseCase.Params
import com.example.test_utils_android.InstantExecutorExtension
import com.example.test_utils_android.getOrAwaitValue
import com.example.test_utils_android.test_utils.createCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class CharacterDetailsViewModelTest {

    private val characterUseCase: GetCharacterUseCase = mockk()

    private lateinit var viewModel: CharacterDetailsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = CharacterDetailsViewModel(characterUseCase = characterUseCase)
    }

    @Test
    fun `Given a valid character ID When fetchCharacters is called Then characterDetail should update with character data`() =
        runTest {
            val characterId = 1
            val characterDisplay = DetailsDisplay(
                hasError = false,
                CharacterDisplay(
                    id = characterId,
                    name = "Rick Sanchez",
                    gender = "Male",
                    status = "Alive",
                    species = "Human",
                    image = "image",
                    created = "04 noviembre 2017, 00:00:00",
                    origin = "Earth",
                    location = "Earth",
                )
            )

            val character = createCharacter()

            coEvery { characterUseCase.run(Params(characterId)) } returns Either.Success(character)

            viewModel.fetchCharacters(characterId)

            val result = viewModel.characterDetail.getOrAwaitValue()

            assertEquals(characterDisplay, result)
            assertFalse(result.hasError)
        }

    @Test
    fun `Given an invalid character ID When fetchCharacters is called Then characterDetail should update with error`() =
        runTest {
            val characterId = -1

            coEvery { characterUseCase.run(Params(characterId)) } returns Either.Error(Throwable("Invalid id"))

            viewModel.fetchCharacters(characterId)

            val result: DetailsDisplay = viewModel.characterDetail.getOrAwaitValue()

            assertTrue(result.hasError)
        }
}