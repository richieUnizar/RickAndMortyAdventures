package com.example.presentation_character_list

import com.example.domain.model.Character
import com.example.domain.model.Characters

data class CharactersDisplay(
    var numberOfCharacters: Int,
    var characterList: List<CharacterDisplay>,
)

data class CharacterDisplay(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val isInFavourites: Boolean,
)

fun Characters.toDisplay() = CharactersDisplay(
    numberOfCharacters = this.info.numberOfCharacters,
    characterList = this.characterList.toDisplay()
)

fun List<Character>.toDisplay() = this.map { it.toDisplay() }

fun Character.toDisplay() = CharacterDisplay(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    image = this.image,
    isInFavourites = this.isInFavourites
)