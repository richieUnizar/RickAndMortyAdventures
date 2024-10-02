package com.example.data.repositories

import com.example.common.Either

import com.example.data_source_rest.characters.CharactersDataSource
import com.example.data_source_rest.model.CharacterDTO
import com.example.data_source_rest.model.CharactersDTO
import com.example.data_source_rest.model.InfoDTO
import com.example.data_source_rest.model.LocationDTO
import com.example.domain.model.Characters
import com.example.domain.model.Info
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import com.example.domain.model.Character
import com.example.domain.model.Location
import com.example.domain.model.Origin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CharactersRepositoryImplTest {

    private val charactersDataSource: CharactersDataSource = mockk()

    private lateinit var charactersRepository: CharactersRepositoryImpl

    @BeforeEach
    fun setup() {
        charactersRepository = CharactersRepositoryImpl(charactersDataSource)
    }

    @Test
    fun `Given a valid page number When getCharacters is called then it returns success with characters`() =
        runTest {
            val page = 1

            coEvery { charactersDataSource.getCharacters(page) } returns Either.Success(
                CHARACTER_DTO
            )

            val result = charactersRepository.getCharacters(page)

            assertTrue(result.isRight())
            assertEquals(EXPECTED_CHARACTER, result.getRightValue())
        }

    @Test
    fun `Given an invalid page number when getCharacters is called then it returns error`() = runTest {
        val page = -1
        val expectedError = Throwable("Invalid page")

        coEvery { charactersDataSource.getCharacters(page) } returns Either.Error(expectedError)

        val result = charactersRepository.getCharacters(page)

        assertTrue(result.isLeft())
        assertEquals(expectedError, result.getLeftValue())
    }

    companion object {
        private val CHARACTER_DTO = CharactersDTO(
            info = InfoDTO(1, 1, null, null),
            results = listOf(
                CharacterDTO(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = LocationDTO(name = "Earth (C-137)", url = "url"),
                    location = LocationDTO(name = "Earth (Replacement Dimension)", url = "url"),
                    image = "image",
                    episode = emptyList(),
                    url = "https://rickandmortyapi.com/api/character/1",
                    created = "2017-11-04T18:48:46.250Z"
                )
            )
        )

        val EXPECTED_CHARACTER = Characters(
            info = Info(1, 1),
            characterList = listOf(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = Origin(name = "Earth (C-137)", url = "url"),
                    location = Location(name = "Earth (Replacement Dimension)", url = "url"),
                    image = "image",
                    episode = emptyList(),
                    url = "https://rickandmortyapi.com/api/character/1",
                    created = "2017-11-04T18:48:46.250Z"
                )
            )
        )
    }
}
