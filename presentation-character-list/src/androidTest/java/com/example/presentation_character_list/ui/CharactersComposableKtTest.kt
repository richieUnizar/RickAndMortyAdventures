package com.example.presentation_character_list.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.presentation_character_list.CharacterDisplay
import com.example.presentation_character_list.ui.CharacterListTestTags.CHARACTER_LIST_TEST_TAG
import com.example.presentation_character_list.ui.CharacterListTestTags.CHARACTER_TEST_TAG
import com.example.presentation_character_list.ui.CharacterListTestTags.HEART_ICON_TEST_TAG
import com.example.presentation_character_list.ui.CharacterListTestTags.NAME_TEST_TAG
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class CharactersComposableKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenCharactersList_whenScreenIsDisplayed_thenFirstCharacterAndRickSanchezAreVisibleAList() {
        composeTestRule.apply {
            setContent {
                CharactersComposable(
                    listState = rememberLazyListState(),
                    characters = creteCharactersDisplay(),
                    onItemClick = {},
                    onHeartIconClick = { _, _ ->}
                )
            }
        }

        composeTestRule.onNodeWithTag(CHARACTER_LIST_TEST_TAG, useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule.onAllNodesWithTag(NAME_TEST_TAG, useUnmergedTree = true)
            .filter(hasText("Rick Sanchez"))
            .onFirst()
            .assertIsDisplayed()

    }


    @Test
    fun givenCharactersList_whenHeartIconIsClicked_thenButtonClickedFlagIsTrue() {
        var isHeartSelected = false
        var characterDisplay: CharacterDisplay? = null

        composeTestRule.apply {
            setContent {
                CharactersComposable(
                    listState = rememberLazyListState(),
                    characters = creteCharactersDisplay(),
                    onItemClick = {},
                    onHeartIconClick = { heartIcon, character  ->
                        isHeartSelected = heartIcon
                        characterDisplay = character
                    }
                )
            }
        }

        composeTestRule.onAllNodesWithTag(HEART_ICON_TEST_TAG)
            .onFirst()
            .performClick()

        assertTrue(isHeartSelected)
        assertNotNull(characterDisplay)
    }

    @Test
    fun givenCharactersList_whenCharacterIsClicked_thenSelectedCharacterIsNotNull() {
        var onItemClicked: CharacterDisplay? = null

        composeTestRule.apply {
            setContent {
                CharactersComposable(
                    listState = rememberLazyListState(),
                    characters = creteCharactersDisplay(),
                    onItemClick = { character ->
                        onItemClicked = character
                    },
                    onHeartIconClick = { _, _  -> }
                )
            }
        }

        composeTestRule.onAllNodesWithTag(CHARACTER_TEST_TAG)
            .onFirst()
            .performClick()

        assertNotNull(onItemClicked)
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