package com.example.data.repositories

import com.example.common.Either
import com.example.data.utils.TestData.CHARACTERS_DTO
import com.example.data.utils.TestData.EXPECTED_CHARACTERS
import com.example.data_source_rest.search.SearchDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchRepositoryImplTest {

    private val searchDataSource: SearchDataSource = mockk()

    private lateinit var repository: SearchRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = SearchRepositoryImpl(searchDataSource)
    }

    @Test
    fun `Given a valid name When getCharacterByName is called Then it returns success with characters`() =
        runTest {
            val name = "Rick"

            coEvery { searchDataSource.getCharacterByName(name) } returns
                    Either.Success(CHARACTERS_DTO)

            val result = repository.getCharacterByName(name)

            assertTrue(result.isRight())
            assertEquals(EXPECTED_CHARACTERS, result.getRightValue())
        }

    @Test
    fun `Given an invalid name when getCharacterByName is called Then it returns error`() =
        runTest {
            val name = "fake"
            val expectedError = Throwable("Invalid page")

            coEvery { searchDataSource.getCharacterByName(name) } returns Either.Error(expectedError)

            val result = repository.getCharacterByName(name)

            assertTrue(result.isLeft())
            assertEquals(expectedError, result.getLeftValue())
        }

}