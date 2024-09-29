package com.example.presentation_favourite_characters.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import com.example.presentation_favourite_characters.CharacterDisplay
import com.example.presentation_favourite_characters.ui.FavouriteCharactersTestTags.NAME_TEST_TAG
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class FavouriteCharactersComposableKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCharactersList_whenScreenIsDisplayed_thenFirstCharacterAndRickSanchezAreVisibleAList() {
        composeTestRule.apply {
            setContent {
                FavouriteCharactersComposable(
                    characters = creteCharactersDisplay(),
                    onItemClick = {},
                    onHeartIconClick = {}
                )
            }
        }

        composeTestRule.onAllNodesWithTag(
            FavouriteCharactersTestTags.FAVOURITE_LIST_TEST_TAG,
            useUnmergedTree = true
        )
            .onFirst()
            .assertIsDisplayed()

        composeTestRule.onAllNodesWithTag(NAME_TEST_TAG, useUnmergedTree = true)
            .filter(hasText("Rick Sanchez"))
            .onFirst()
            .assertIsDisplayed()

    }

    @Test
    fun givenCharactersList_whenHeartIconIsClicked_thenButtonClickedFlagIsTrue() {
        var buttonClicked = false

        composeTestRule.apply {
            setContent {
                FavouriteCharactersComposable(
                    characters = creteCharactersDisplay(),
                    onItemClick = {},
                    onHeartIconClick = {
                        buttonClicked = true
                    }
                )
            }
        }

        composeTestRule.onAllNodesWithTag(FavouriteCharactersTestTags.HEART_ICON_TEST_TAG)
            .onFirst()
            .performClick()

        Assert.assertTrue(buttonClicked)
    }

    @Test
    fun givenCharactersList_whenCharacterIsClicked_thenSelectedCharacterIsNotNull() {
        var onItemClicked: CharacterDisplay? = null

        composeTestRule.apply {
            setContent {
                FavouriteCharactersComposable(
                    characters = creteCharactersDisplay(),
                    onItemClick = {
                        onItemClicked = it
                    },
                    onHeartIconClick = {}
                )
            }
        }

        composeTestRule.onAllNodesWithTag(FavouriteCharactersTestTags.CHARACTER_TEST_TAG)
            .onFirst()
            .performClick()

        Assert.assertNotNull(onItemClicked)
    }


    private fun creteCharactersDisplay() = mutableListOf(
        CharacterDisplay(
            id = 0,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Humanoid",
            image = "Image",
            isInFavourites = false
        ),
        CharacterDisplay(
            id = 0,
            name = "Morty Smith",
            status = "Alive",
            species = "Humanoid",
            image = "Image",
            isInFavourites = false
        ),
        CharacterDisplay(
            id = 0,
            name = "Summer Smith",
            status = "Alive",
            species = "Humanoid",
            image = "Image",
            isInFavourites = false
        ),
        CharacterDisplay(
            id = 0,
            name = "Beth Smith",
            status = "Alive",
            species = "Humanoid",
            image = "Image",
            isInFavourites = false
        )
    )

}