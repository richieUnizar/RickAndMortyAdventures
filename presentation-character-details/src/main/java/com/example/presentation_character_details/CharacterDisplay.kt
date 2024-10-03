package com.example.presentation_character_details

import com.example.domain.model.Character

data class CharacterDisplay(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val hasError: Boolean,
)

fun Character.toDisplay() = CharacterDisplay(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    image = this.image,
    hasError = false
)
