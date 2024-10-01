package com.example.presentation_favourite_characters

import com.example.common.Either
import com.example.domain.Favourites.get_list.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import com.example.domain.Favourites.model.FavouriteCharacter
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FavouriteCharactersViewModelTest {

    private val getFavouriteCharactersUseCase: GetFavouriteCharactersUseCase = mockk()
    private val removeToFavouriteUseCase: RemoveToFavouriteUseCase = mockk()

    private lateinit var viewModel: FavouriteCharactersViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        coEvery { getFavouriteCharactersUseCase.getFavoriteCharacters() } returns Either.Success(
            CHARACTER_LIST
        )

        viewModel = FavouriteCharactersViewModel(
            getFavouriteCharactersUseCase = getFavouriteCharactersUseCase,
            removeToFavouriteUseCase = removeToFavouriteUseCase
        )

        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given favourite characters When ViewModel is initialized Then stateFlow emits characters`() =
        runTest {

            advanceUntilIdle()

            assertEquals(CHARACTER_LIST.toFavouriteCharactersDisplay(), viewModel.charactersDisplay.first())
        }

    @Test
    fun `when removeCharacterFromFavourites is called Then the character should be removed from charactersDisplay`() =
        runTest {
            advanceUntilIdle()

            coEvery { removeToFavouriteUseCase.removeCharacterFromFavorites(1) } just Runs


            viewModel.removeCharacterFromFavourites(1)

            val charactersDisplayState =
                viewModel.charactersDisplay.first { it.characterList.size == 1 }.characterList

            coVerify(exactly = 1) {
                removeToFavouriteUseCase.removeCharacterFromFavorites(1)
            }

            assertEquals(1, charactersDisplayState.size)
            assertFalse(charactersDisplayState.any { it.id == 1 })
        }


    companion object {

        val CHARACTER_LIST = listOf(
            FavouriteCharacter(id = 1, name = "Rick", status = "Alive", species = "Human", image = "image1"),
            FavouriteCharacter(id = 2, name = "Morty", status = "Alive", species = "Human", image = "image2")
        )

    }

}
