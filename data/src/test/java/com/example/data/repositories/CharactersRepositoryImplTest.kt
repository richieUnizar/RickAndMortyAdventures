package com.example.data.repositories

import com.example.common.Either
import com.example.data.utils.TestData.CHARACTERS_DTO
import com.example.data.utils.TestData.EXPECTED_CHARACTERS
import com.example.data_source_rest.characters.CharactersDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CharactersRepositoryImplTest {

    private val charactersDataSource: CharactersDataSource = mockk()

    private lateinit var repository: CharactersRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = CharactersRepositoryImpl(charactersDataSource)
    }

    @Test
    fun `Given a valid page number When getCharacters is called Then it returns success with characters`() =
        runTest {
            val page = 1

            coEvery { charactersDataSource.getCharacters(page) } returns
                    Either.Success(CHARACTERS_DTO)

            val result = repository.getCharacters(page)

            assertTrue(result.isRight())
            assertEquals(EXPECTED_CHARACTERS, result.getRightValue())
        }

    @Test
    fun `Given an invalid page number when getCharacters is called Then it returns error`() = runTest {
        val page = -1
        val expectedError = Throwable("Invalid page")

        coEvery { charactersDataSource.getCharacters(page) } returns Either.Error(expectedError)

        val result = repository.getCharacters(page)

        assertTrue(result.isLeft())
        assertEquals(expectedError, result.getLeftValue())
    }

}
