package com.example.presentation_character_list

import com.example.common.Either
import com.example.domain.Favourites.AddToFavouriteUseCase
import com.example.domain.Favourites.GetFavouriteCharactersUseCase
import com.example.domain.Favourites.RemoveToFavouriteUseCase
import com.example.domain.characters.Character
import com.example.domain.characters.Characters
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.characters.Info
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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


class CharacterListViewModelTest {

    private val charactersUseCase: GetCharactersUseCase = mockk()

    private val favouriteCharacterUseCase: AddToFavouriteUseCase = mockk()

    private val removeToFavouriteUseCase: RemoveToFavouriteUseCase = mockk()

    private lateinit var viewModel: CharacterListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        val characters =
            Characters(Info(numberOfCharacters = 2, numberOfPages = 1), CHARACTER_LIST)

        coEvery { charactersUseCase.getCharacters(any()) } returns Either.Success(characters)

        viewModel = CharacterListViewModel(
            charactersUseCase,
            favouriteCharacterUseCase,
            removeToFavouriteUseCase
        )

        Dispatchers.setMain(testDispatcher)

    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchCharacters is called, then characterList should be updated`() = runTest {

        advanceUntilIdle()

        val charactersDisplayState = viewModel.characterList.first().characterList

        assertEquals(2, charactersDisplayState.size)

        coVerify(exactly = 1) {
            charactersUseCase.getCharacters(1)
        }
    }

    @Test
    fun `when onHeartIconClicked is called with true, then character should be added to favourites`() =
        runTest {
            val characterDisplay = CharacterDisplay(
                id = 1,
                name = "Rick",
                status = "Alive",
                species = "Human",
                image = "image1",
                isInFavourites = false
            )

            coEvery { favouriteCharacterUseCase.addCharacterToFavorites(any()) } just Runs

            advanceUntilIdle()

            viewModel.onHeartIconClicked(true, characterDisplay)

            advanceUntilIdle()

            coVerify(exactly = 1) {
                favouriteCharacterUseCase.addCharacterToFavorites(
                    AddToFavouriteUseCase.Params(CHARACTER_LIST.first())
                )
            }
        }

    @Test
    fun `when onHeartIconClicked is called with false, then character should be removed from favourites`() =
        runTest {
            val characterDisplay = CharacterDisplay(
                id = 1,
                name = "Rick",
                status = "Alive",
                species = "Human",
                image = "image1",
                isInFavourites = true
            )

            coEvery { removeToFavouriteUseCase.removeCharacterFromFavorites(any()) } just Runs

            advanceUntilIdle()

            viewModel.onHeartIconClicked(false, characterDisplay)

            advanceUntilIdle()

            coVerify(exactly = 1) { removeToFavouriteUseCase.removeCharacterFromFavorites(1) }
        }


    companion object {
        val CHARACTER_LIST = listOf(
            Character(id = 1, name = "Rick", status = "Alive", species = "Human", image = "image1"),
            Character(id = 2, name = "Morty", status = "Alive", species = "Human", image = "image2")
        )

    }
}