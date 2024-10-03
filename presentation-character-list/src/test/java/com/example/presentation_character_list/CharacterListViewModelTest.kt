package com.example.presentation_character_list

import com.example.common.Either
import com.example.domain.Favourites.add.AddToFavouriteUseCase
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.characters.GetCharactersUseCase.*
import com.example.domain.model.Info
import com.example.domain.model.Location
import com.example.domain.model.Origin
import io.mockk.Runs
import io.mockk.clearAllMocks
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
import org.junit.jupiter.api.Assertions.assertTrue
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
            Characters(Info(numberOfCharacters = 20, numberOfPages = 2), generateCharacterList(20))

        coEvery { charactersUseCase.run(any()) } returns Either.Success(characters)

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

        assertEquals(20, charactersDisplayState.size)

        coVerify(exactly = 1) {
            charactersUseCase.run(Params(1))
        }
    }

    @Test
    fun `when loadNextPage is called, then fetchCharacters should be called if near end of list`() =
        runTest {

            advanceUntilIdle()

            viewModel.loadNextPage(10)

            advanceUntilIdle()

            coVerify(exactly = 1) {
                charactersUseCase.run(Params(1))
                charactersUseCase.run(Params(2))
            }
        }

    @Test
    fun `when onHeartIconClicked is called with true, then character should be added to favourites`() =
        runTest {
            val expectedCharacterParam = AddToFavouriteUseCase.Params(
                generateCharacterList(1).first()
            )
            val characterDisplay = CharacterDisplay(
                id = 1,
                name = "Character 1",
                status = "Alive",
                species = "Human",
                image = "image1",
                isInFavourites = false
            )

            coEvery { favouriteCharacterUseCase.addCharacterToFavorites(any()) } just Runs

            advanceUntilIdle()

            viewModel.onHeartIconClicked(true, characterDisplay)

            advanceUntilIdle()

            val firstDisplay = viewModel.characterList.getOrAwaitValue().characterList.first()

            assertTrue(firstDisplay.isInFavourites)

            coVerify(exactly = 1) {
                favouriteCharacterUseCase.addCharacterToFavorites(expectedCharacterParam)
            }
        }

    @Test
    fun `when onHeartIconClicked is called with false, then character should be removed from favourites`() =
        runTest {
            val characterDisplay = CharacterDisplay(
                id = 1,
                name = "Character 1",
                status = "Alive",
                species = "Human",
                image = "image1",
                isInFavourites = true
            )

            coEvery { removeToFavouriteUseCase.removeCharacterFromFavorites(any()) } just Runs

            advanceUntilIdle()

            viewModel.onHeartIconClicked(false, characterDisplay)

            advanceUntilIdle()

            val firstDisplay = viewModel.characterList.first().characterList.first()

            assertFalse(firstDisplay.isInFavourites)

            coVerify(exactly = 1) { removeToFavouriteUseCase.removeCharacterFromFavorites(1) }
        }

    private fun generateCharacterList(numberOfItems: Int): List<Character> {
        val characterList = mutableListOf<Character>()

        for (i in 1..numberOfItems) {
            characterList.add(
                Character(
                    id = i,
                    name = "Character $i",
                    status = "Alive",
                    species = "Alien",
                    image = "image$i",
                    type = "type",
                    gender = "gender",
                    origin = Origin("", ""),
                    location = Location("", ""),
                    episode = emptyList(),
                    url = "url",
                    created = "created",
                    isInFavourites = false,
                )
            )
        }

        return characterList
    }
}