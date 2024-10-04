package com.example.presentation_character_details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.presentation_character_details.CharacterDisplay


@Composable
fun CharacterDetailsComposable(
    character: CharacterDisplay,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        Column(modifier = Modifier.padding(16.dp)){

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
fun CharacterDefinitionRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}
