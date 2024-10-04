package com.example.presentation_base.ui.alert_dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.CONFIRM_BUTTON_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.DISMISS_BUTTON_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.ICON_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.TEXT_TEST_TAG
import com.example.presentation_base.ui.alert_dialog.AlertDialogTestTags.TITLE_TEST_TAG

object AlertDialogTestTags {
    const val TITLE_TEST_TAG = "TitleTestTab"
    const val TEXT_TEST_TAG = "TitleTestTab"
    const val ICON_TEST_TAG = "IconTestTab"
    const val CONFIRM_BUTTON_TEST_TAG = "confirmButtonTestTab"
    const val DISMISS_BUTTON_TEST_TAG = "dismissButtonTestTab"
}


@Composable
fun AlertDialogComposable(
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector? = null,
    onConfirmation: () -> Unit,
    onDismissRequest: (() -> Unit)? = null,
) {
    AlertDialog(
        icon = icon?.let {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.testTag(ICON_TEST_TAG)
                )
            }
        },
        title = {
            Text(
                text = dialogTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TITLE_TEST_TAG),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = dialogText,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TEXT_TEST_TAG),
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
                modifier = Modifier.testTag(CONFIRM_BUTTON_TEST_TAG)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = if (onDismissRequest != null) {
            {
                TextButton(
                    onClick = {
                        onConfirmation()
                    },
                    modifier = Modifier.testTag(DISMISS_BUTTON_TEST_TAG)
                ) {
                    Text("Dismiss")
                }
            }
        } else null
    )
}

@Preview
@Composable
fun PreviewAlertDialogComposable() {
    AlertDialogComposable(
        dialogTitle = "Something went wrong",
        dialogText = "The character could not be displayed",
        icon = Icons.Default.Info,
        onConfirmation = { },
        onDismissRequest = { }
    )
}