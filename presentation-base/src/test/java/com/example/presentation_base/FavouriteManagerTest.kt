package com.example.presentation_base

import com.example.domain.Favourites.add.AddToFavouriteUseCase
import com.example.domain.Favourites.remove.RemoveToFavouriteUseCase
import com.example.test_utils_android.test_utils.createFavouriteCharacter
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FavouriteManagerTest {

    private lateinit var favouriteManager: FavouriteManager

    private val addToFavouriteUseCase: AddToFavouriteUseCase = mockk(relaxed = true)
    private val removeToFavouriteUseCase: RemoveToFavouriteUseCase = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        favouriteManager = FavouriteManager(addToFavouriteUseCase, removeToFavouriteUseCase)
    }

    @Test
    fun `Given heart icon clicked and isHeartSelected is true When handleHeartIconClick is called Then character is added to favourites`() =
        runTest {
            val favouriteCharacter = createFavouriteCharacter()

            favouriteManager.handleHeartIconClick(favouriteCharacter, isHeartSelected = true)

            coVerify(exactly = 1) {
                addToFavouriteUseCase.addCharacterToFavorites(
                    AddToFavouriteUseCase.Params(
                        favouriteCharacter
                    )
                )
            }
            coVerify(exactly = 0) {
                removeToFavouriteUseCase.removeCharacterFromFavorites(any())
            }
        }

    @Test
    fun `Given heart icon clicked and isHeartSelected is false When handleHeartIconClick is called Then character is removed from favourites`() =
        runTest {
            val favouriteCharacter = createFavouriteCharacter()

            favouriteManager.handleHeartIconClick(favouriteCharacter, isHeartSelected = false)

            coVerify(exactly = 1) {
                removeToFavouriteUseCase.removeCharacterFromFavorites(favouriteCharacter.id)
            }
            coVerify(exactly = 0) {
                addToFavouriteUseCase.addCharacterToFavorites(any())
            }
        }

}
