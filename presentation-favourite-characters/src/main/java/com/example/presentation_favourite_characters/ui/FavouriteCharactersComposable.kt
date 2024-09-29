package com.example.presentation_favourite_characters.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.presentation_favourite_characters.CharacterDisplay
import com.example.presentation_favourite_characters.ui.FavouriteCharactersTestTags.CHARACTER_TEST_TAG
import com.example.presentation_favourite_characters.ui.FavouriteCharactersTestTags.HEART_ICON_TEST_TAG
import com.example.presentation_favourite_characters.ui.FavouriteCharactersTestTags.FAVOURITE_LIST_TEST_TAG
import com.example.presentation_favourite_characters.ui.FavouriteCharactersTestTags.NAME_TEST_TAG

object FavouriteCharactersTestTags {
    const val FAVOURITE_LIST_TEST_TAG = "FavouriteListTestTab"
    const val NAME_TEST_TAG = "NameTestTab"
    const val HEART_ICON_TEST_TAG = "FavouriteListHeartIconTestTab"
    const val CHARACTER_TEST_TAG = "CharacterTestTab"
}


@Composable
fun FavouriteCharactersComposable(
    characters: List<CharacterDisplay>,
    onItemClick: (CharacterDisplay) -> Unit,
    onHeartIconClick: (CharacterDisplay) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier
            .testTag(FAVOURITE_LIST_TEST_TAG)
    ) {
        items(characters) { character ->
            TwoCharactersPerRow(
                character = character,
                onItemClick = onItemClick,
                onHeartIconClick = onHeartIconClick
            )
        }
    }
}

@Composable
fun TwoCharactersPerRow(
    character: CharacterDisplay,
    onItemClick: (CharacterDisplay) -> Unit,
    onHeartIconClick: (CharacterDisplay) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(character)
            }
            .padding(8.dp)
            .testTag(CHARACTER_TEST_TAG)
    ) {
        Box {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(
                onClick = {
                    onHeartIconClick(character)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .testTag(HEART_ICON_TEST_TAG)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = character.name,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp),
                fontWeight = FontWeight.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .testTag(NAME_TEST_TAG),
            )
            Text(
                text = character.species,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CharactersComposablePreview() {
    FavouriteCharactersComposable(
        mutableListOf(
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
        ),
        onItemClick = {},
        onHeartIconClick = { _ -> }
    )

}