package com.example.presentation_character_details

import com.example.common.Either
import com.example.domain.character.GetCharacterUseCase
import com.example.domain.character.GetCharacterUseCase.Params
import com.example.domain.model.Character
import com.example.domain.model.Location
import com.example.domain.model.Origin
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
            val characterDisplay = CharacterDisplay(
                id = characterId,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "image",
                hasError = false
            )
            val character = createCharacter()

            coEvery { characterUseCase.run(Params(characterId)) } returns Either.Success(character)

            viewModel.fetchCharacters(characterId)

            val result = viewModel.characterDetail.take(1).first()

            assertEquals(characterDisplay, result)
            assertFalse(result.hasError)
        }

    @Test
    fun `Given an invalid character ID When fetchCharacters is called Then characterDetail should update with error`() =
        runTest {
            val characterId = -1

            coEvery { characterUseCase.run(Params(characterId)) } returns Either.Error(Throwable("Invalid id"))

            viewModel.fetchCharacters(characterId)

            val result = viewModel.characterDetail.first()
            assertTrue(result.hasError)
        }


    private fun createCharacter() = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = null,
        gender = "Male",
        origin = Origin(name = "Earth", url = "url"),
        location = Location(name = "Earth", url = "url"),
        image = "image",
        episode = listOf("episode 1"),
        url = "url",
        created = "2017-11-04",
        isInFavourites = false
    )
}