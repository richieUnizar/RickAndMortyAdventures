package com.example.domain.Favourites.remove

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class RemoveToFavouriteUseCaseTest {

    private val repository: RemoveToFavouritesRepository = mockk()

    private val useCase = RemoveToFavouriteUseCase(repository)

    @Test
    fun `Given an id When removeCharacterFromFavorites is called Then repository remove character to favourites`() =
        runTest {
            val characterId = 1

            coEvery { repository.removeCharacterFromFavorites(characterId) } just Runs

            useCase.removeCharacterFromFavorites(characterId)

            coVerify(exactly = 1) {
                repository.removeCharacterFromFavorites(characterId)
            }
        }


}