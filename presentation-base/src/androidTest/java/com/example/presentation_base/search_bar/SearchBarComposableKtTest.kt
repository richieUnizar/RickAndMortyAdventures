package com.example.presentation_base.search_bar

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import com.example.presentation_base.ui.search_bar.SearchBarComposable
import com.example.presentation_base.ui.search_bar.SearchBarTestTags.PLACEHOLDER_TEST_TAG
import com.example.presentation_base.ui.search_bar.SearchBarTestTags.SUGGESTIONS_LIST_TEST_TAG
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class SearchBarComposableKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPlaceholderText_whenScreenIsDisplayed_thenPlaceholderIsVisible() {

        val placeholder = "Search by name"

        composeTestRule.apply {
            setContent {
                SearchBarComposable(
                    placeholder = placeholder,
                    suggestionsList = emptyList(),
                    onSearchClick = {},
                )
            }
        }

        composeTestRule.onNode(hasTestTag(PLACEHOLDER_TEST_TAG).and(hasText(placeholder)), useUnmergedTree = true)
            .assertIsDisplayed()
            .onParent()
    }

    @Test
    fun givenSuggestionsList_whenLastSuggestionIsClicked_thenSelectedCharacterMatches() {

        val expectedCharacter = "Morty"
        var onItemClicked: String? = null

        composeTestRule.apply {
            setContent {
                SearchBarComposable(
                    placeholder = "placeholder",
                    suggestionsList = listOf("Rick", "Morty"),
                    onSearchClick = { text ->
                        onItemClicked = text
                    },
                )
            }
        }

        composeTestRule.onAllNodesWithTag(SUGGESTIONS_LIST_TEST_TAG)
            .onLast()
            .performClick()

        assertEquals(expectedCharacter, onItemClicked)
    }
}