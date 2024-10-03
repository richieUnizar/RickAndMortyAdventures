package com.example.presentation_base.search_bar


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation_base.search_bar.SearchBarTestTags.PLACEHOLDER_TEST_TAG
import com.example.presentation_base.search_bar.SearchBarTestTags.SUGGESTIONS_LIST_TEST_TAG

object SearchBarTestTags {
    const val PLACEHOLDER_TEST_TAG = "PlaceholderTestTab"
    const val SUGGESTIONS_LIST_TEST_TAG = "suggestionListTestTab"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComposable(
    placeholder: String,
    suggestionsList: List<String>,
    onSearchClick: (String) -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchText,
                    onQueryChange = { searchText = it },
                    onSearch = {
                        onSearchClick(searchText)
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text(placeholder, modifier = Modifier.testTag(PLACEHOLDER_TEST_TAG)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                )
            },
            expanded = true,
            onExpandedChange = { expanded = true },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                suggestionsList.forEach { member ->
                    ListItem(
                        headlineContent = { Text(member) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .clickable {
                                searchText = member
                                expanded = false
                                onSearchClick(member)
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .testTag(SUGGESTIONS_LIST_TEST_TAG)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchBarCPreview() {
    SearchBarComposable(
        placeholder = "Search",
        suggestionsList = emptyList(),
        onSearchClick = {}
    )
}
