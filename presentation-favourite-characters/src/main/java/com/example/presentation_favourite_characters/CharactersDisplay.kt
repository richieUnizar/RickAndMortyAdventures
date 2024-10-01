package com.example.presentation_favourite_characters

import com.example.domain.Favourites.model.FavouriteCharacter
import com.example.domain.model.Character

data class CharactersDisplay(
    var characterList: List<CharacterDisplay>
)

data class CharacterDisplay(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val isInFavourites: Boolean,
)

fun List<Character>.toDisplay() = CharactersDisplay(
    characterList = this.map { it.toDisplay() }
)

fun Character.toDisplay() = CharacterDisplay(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    image = this.image,
    isInFavourites = this.isInFavourites
)

fun List<FavouriteCharacter>.toFavouriteCharactersDisplay() = CharactersDisplay(
    characterList = this.map { it.toFavouriteCharacterDisplay() }
)

fun FavouriteCharacter.toFavouriteCharacterDisplay() = CharacterDisplay(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    image = this.image,
    isInFavourites = true
)