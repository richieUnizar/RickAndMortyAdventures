package com.example.domain.Favourites.get_list

import com.example.common.Either
import com.example.domain.Favourites.model.FavouriteCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetFavouriteCharactersUseCaseTest {

    private val repository: FavouriteCharactersRepository = mockk()

    private val useCase = GetFavouriteCharactersUseCase(repository)

    @Test
    fun `Given a list of favourite characters When getFavoriteCharacters is called Then the correct list of favourite characters is returned`() =
        runTest {
            val favouriteList = listOf(
                FavouriteCharacter(
                    id = 1,
                    name = "Rick",
                    status = "Alive",
                    species = "Human",
                    image = "https://example.com/image1.png"
                )
            )

            coEvery { repository.getFavoriteCharacters() } returns Either.Success(favouriteList)

            val result = useCase.run()

            assertEquals(favouriteList, result.getRightValue())
        }

    @Test
    fun `Given get favorite characters returns error When getFavoriteCharacters is called Then the error is returned`() =
        runTest {
            val error = Throwable("Error getting character detail")

            coEvery { repository.getFavoriteCharacters() } returns Either.Error(error)

            val result = useCase.run()

            assertEquals(error, result.getLeftValue())
        }

}