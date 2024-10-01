package com.example.domain.character

import com.example.common.Either
import com.example.domain.model.Character
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetCharacterUseCaseTest {

    private val repository: CharacterRepository = mockk()

    private val useCase = GetCharacterUseCase(repository)

    @Test
    fun `Given character repository a character When is getCharacter called Then the use case return a character`() =
        runTest {
            val character = mockk<Character>()

            coEvery { repository.getCharacter(CHARACTER_ID) } returns Either.Success(character)

            val result = useCase.getCharacter(CHARACTER_ID)

            assertEquals(character, result.getRightValue())
        }

    @Test
    fun `Given character repository returns error When getCharacters is called Then the error is returned`() =
        runTest {
            val error = Throwable("Error getting character detail")

            coEvery { repository.getCharacter(CHARACTER_ID) } returns Either.Error(error)


            val result = useCase.getCharacter(CHARACTER_ID)

            assertEquals(error, result.getLeftValue())
        }

    companion object {
        private val CHARACTER_ID = 1
    }

}