package com.example.presentation_character_list

import com.example.common.asSuccess
import com.example.domain.characters.GetCharactersUseCase
import com.example.domain.characters.GetCharactersUseCase.Params
import com.example.domain.model.Characters
import com.example.presentation_base.FavouriteManager
import com.example.test_utils_android.InstantExecutorExtension
import com.example.test_utils_android.TestCoroutineExtension
import com.example.test_utils_android.getOrAwaitValue
import com.example.test_utils_android.test_utils.createCharacters
import com.example.test_utils_android.test_utils.generateCharacter
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class, TestCoroutineExtension::class)
class CharacterListViewModelTest {

    private val charactersUseCase: GetCharactersUseCase = mockk()

    private val favouriteManager: FavouriteManager = mockk()

    private lateinit var viewModel: CharacterListViewModel

    @BeforeEach
    fun setup(){
        viewModel = CharacterListViewModel(charactersUseCase, favouriteManager)

    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given ViewModel initialized When view model is initialized is called Then characterList is updated`() =
        runTest {
            val characters: Characters = createCharacters()

            coEvery { charactersUseCase.run(any()) } returns characters.asSuccess()

            viewModel.intiPage(null)

            val charactersDisplayState = viewModel.characters.getOrAwaitValue()

            assertEquals(1, charactersDisplayState.characterList.size)

            coVerify(exactly = 1) {
                charactersUseCase.run(Params(1))
            }
        }

    @Test
    fun `Given near end of list When loadNextPage is called Then fetchCharacters should be called`() =
        runTest {
            val characters = generateCharacter(numberOfPages = 2, numberOfCharacters = 20)

            coEvery { charactersUseCase.run(any()) } returns characters.asSuccess()

            viewModel.intiPage(null)

            viewModel.loadNextPage(10)

            coVerify {
                charactersUseCase.run(Params(1))
                charactersUseCase.run(Params(2))
            }
        }

    @Test
    fun `Given heart icon clicked with true When onHeartIconClicked is called Then character should be added to favourites`() =
        runTest {
            val characterDisplay = createCharacterDisplay(isInFavourites = false)
            val favouriteCharacter = characterDisplay.mapToFavouriteCharacter()

            coEvery { charactersUseCase.run(any()) } returns createCharacters().asSuccess()
            coEvery {
                favouriteManager.handleHeartIconClick(
                    favouriteCharacter = favouriteCharacter,
                    isHeartSelected = true
                )
            } just Runs

            viewModel.intiPage(null)

            viewModel.onHeartIconClicked(characterDisplay, isHeartSelected = true)

            val charactersDisplayState = viewModel.characters.getOrAwaitValue()
            assertTrue(charactersDisplayState.characterList.first().isInFavourites)
        }


    @Test
    fun `Given heart icon clicked with false When onHeartIconClicked is called Then character should be removed from favourites`() =
        runTest {
            val characterDisplay = createCharacterDisplay(isInFavourites = true)
            val favouriteCharacter = characterDisplay.mapToFavouriteCharacter()

            coEvery { charactersUseCase.run(any()) } returns createCharacters().asSuccess()
            coEvery {
                favouriteManager.handleHeartIconClick(
                    favouriteCharacter = favouriteCharacter,
                    isHeartSelected = false
                )
            } just Runs

            viewModel.intiPage(null)

            viewModel.onHeartIconClicked(characterDisplay, isHeartSelected = false)

            val charactersDisplayState = viewModel.characters.getOrAwaitValue()
            assertFalse(charactersDisplayState.characterList.first().isInFavourites)
        }

    private fun createCharacterDisplay(isInFavourites: Boolean) = CharacterDisplay(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        image = "image",
        isInFavourites = isInFavourites
    )
}