package com.example.presentation_character_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.presentation_character_details.CharacterDisplay

@Composable
fun CharacterDetailsComposable(
    character: CharacterDisplay,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onHeartIconClick: (isHeartSelected: Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        ImageWithFavoriteButton(
            imageUrl = character.image,
            isFavorite = isFavorite,
            onHeartIconClick = { newFavoriteStatus ->
                onHeartIconClick(newFavoriteStatus)
            }
        )

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = character.name, fontWeight = FontWeight.Bold)

            CharacterDefinitionRow("Status", character.status)
            CharacterDefinitionRow("Gender", character.gender)
            CharacterDefinitionRow("Created", character.created)
            CharacterDefinitionRow("Origin", character.origin)
            CharacterDefinitionRow("Location", character.location)
        }
    }
}

@Composable
private fun CharacterDefinitionRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}

@Composable
private fun ImageWithFavoriteButton(
    imageUrl: String,
    isFavorite: Boolean,
    onHeartIconClick: (Boolean) -> Unit
) {
    Box {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FavoriteIconButton(
                isFavorite = isFavorite,
                onHeartIconClick = { newFavoriteStatus ->
                    onHeartIconClick(newFavoriteStatus)
                }
            )
        }
    }
}

@Composable
private fun FavoriteIconButton(
    isFavorite: Boolean,
    onHeartIconClick: (Boolean) -> Unit
) {
    IconButton(
        onClick = {
            onHeartIconClick(!isFavorite)
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
    ) {
        androidx.compose.material3.Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = Color.White
        )
    }
}

