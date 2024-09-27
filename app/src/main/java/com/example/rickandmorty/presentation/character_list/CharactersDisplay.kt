package com.example.rickandmorty.presentation.character_list

import com.example.domain.characters.Character
import com.example.domain.characters.Characters

data class CharactersDisplay(
    var characterList: List<CharacterDisplay>
)

data class CharacterDisplay(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
)

fun Characters.toDisplay() = CharactersDisplay(
    characterList = this.characterList.map { it.toDisplay() }
)


fun Character.toDisplay() = CharacterDisplay(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    image = this.image
)