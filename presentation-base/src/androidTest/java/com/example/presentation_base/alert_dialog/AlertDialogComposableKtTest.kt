package com.example.presentation_base.alert_dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.presentation_base.ui.alert_dialog.AlertDialogComposable
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.CONFIRM_BUTTON_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.DISMISS_BUTTON_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.ICON_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.TEXT_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.TITLE_TEST_TAG
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class AlertDialogComposableKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenDialogWithAllComponents_whenDialogIsDisplayed_thenTitleTextIconAndButtonsAreVisible() {

        val dialogTitle = "Something went wrong"
        val dialogText = "The character could not be displayed"
        val expectedConfirmText = "Confirm"
        val expectedDismissText = "Dismiss"

        composeTestRule.apply {
            setContent {
                AlertDialogComposable(
                    dialogTitle = "Something went wrong",
                    dialogText = "The character could not be displayed",
                    icon = Icons.Default.Info,
                    onConfirmation = {},
                    onDismissRequest = {}
                )
            }
        }

        composeTestRule.onNode(
            hasTestTag(TITLE_TEST_TAG).and(hasText(dialogTitle)),
            useUnmergedTree = true
        ).assertIsDisplayed()

        composeTestRule.onNode(
            hasTestTag(TEXT_TEST_TAG).and(hasText(dialogText)),
            useUnmergedTree = true
        ).assertIsDisplayed()

        composeTestRule.onNodeWithTag(ICON_TEST_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(CONFIRM_BUTTON_TEST_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(expectedConfirmText)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(DISMISS_BUTTON_TEST_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(expectedDismissText)
            .assertIsDisplayed()

    }


    @Test
    fun givenDialogWithoutIcon_whenDialogIsDisplayed_thenIconIsNotVisible() {

        composeTestRule.apply {
            setContent {
                AlertDialogComposable(
                    dialogTitle = "Something went wrong",
                    dialogText = "The character could not be displayed",
                    icon = null,
                    onConfirmation = { }
                )
            }
        }

        composeTestRule.onNodeWithTag(ICON_TEST_TAG).assertDoesNotExist()

    }

    @Test
    fun givenDialogWithoutDismissButton_whenConfirmButtonIsClicked_thenConfirmationCallbackIsTriggered() {

        var onConfirmationClicked = false

        composeTestRule.apply {
            setContent {
                AlertDialogComposable(
                    dialogTitle = "Something went wrong",
                    dialogText = "The character could not be displayed",
                    icon = Icons.Default.Info,
                    onConfirmation = {
                        onConfirmationClicked = true
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag(DISMISS_BUTTON_TEST_TAG)
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag(CONFIRM_BUTTON_TEST_TAG)
            .performClick()

        assertTrue(onConfirmationClicked)
    }


}