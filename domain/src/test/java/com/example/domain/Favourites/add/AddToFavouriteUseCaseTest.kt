package com.example.domain.Favourites.add

import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.model.Character
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AddToFavouriteUseCaseTest() {

    private val repository: AddToFavouritesRepository = mockk()

    private val useCase = AddToFavouriteUseCase(repository)

    @Test
    fun `Given a character When addCharacterToFavorites is called Then repository add character to favourites`() =
        runTest {
            val character = mockk<FavouriteCharacter>()
            val params = AddToFavouriteUseCase.Params(character)

            coEvery { repository.addCharacterToFavorites(any()) } just Runs

            useCase.addCharacterToFavorites(params)

            coVerify(exactly = 1) {
                repository.addCharacterToFavorites(character)
            }
        }
}