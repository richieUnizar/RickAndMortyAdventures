package com.example.data.repositories

import com.example.common.Either
import com.example.data.utils.TestData.CHARACTER_DTO
import com.example.data.utils.TestData.EXPECTED_CHARACTER
import com.example.data_source_rest.character.CharacterDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CharacterRepositoryImplTest{

    private val characterDataSource: CharacterDataSource = mockk()

    private lateinit var repository: CharacterRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = CharacterRepositoryImpl(characterDataSource)
    }

    @Test
    fun `Given a valid character id When getCharacter is called Then it returns success with character`() =
        runTest {
            val characterId = 1

            coEvery { characterDataSource.getCharacterById(characterId) } returns
                    Either.Success(CHARACTER_DTO)

            val result = repository.getCharacter(characterId)

            assertTrue(result.isRight())
            assertEquals(EXPECTED_CHARACTER, result.getRightValue())
        }

    @Test
    fun `Given an invalid character id when getCharacter is called Then it returns error`() = runTest {
        val characterId = -1
        val expectedError = Throwable("Invalid id")

        coEvery { characterDataSource.getCharacterById(characterId) } returns Either.Error(expectedError)

        val result = repository.getCharacter(characterId)

        assertTrue(result.isLeft())
        assertEquals(expectedError, result.getLeftValue())
    }
}