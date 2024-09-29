package com.example.presentation_character_list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.presentation_character_list.CharacterDisplay

@Composable
fun CharactersComposable(
    listState: LazyListState,
    characters: List<CharacterDisplay>,
    onItemClick: (CharacterDisplay) -> Unit,
    onHeartIconClick: (isHeartSelected: Boolean, CharacterDisplay) -> Unit
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    ) {
        items(characters) { character ->
            CharacterCard(character, onItemClick, onHeartIconClick)
        }
    }
}

@Composable
fun CharacterCard(
    character: CharacterDisplay,
    onItemClick: (CharacterDisplay) -> Unit,
    onHeartIconClick: (isHeartSelected: Boolean, CharacterDisplay) -> Unit
) {
    var isFavorite by remember { mutableStateOf(character.isInFavourites) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(character) },
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp)
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp)
                )

                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = character.name,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 16.sp),
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(bottom = 4.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = character.species,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        text = character.status,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

            }
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    onHeartIconClick(isFavorite, character)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFavoriteCollectionCard() {
    CharacterCard(
        character = CharacterDisplay(
            id = 0,
            image = "https://example.com/image.png",
            name = "Character Name",
            species = "Species",
            status = "Status",
            isInFavourites = false,
        ),
        onItemClick = {},
        onHeartIconClick = { _, _ -> }
    )
}