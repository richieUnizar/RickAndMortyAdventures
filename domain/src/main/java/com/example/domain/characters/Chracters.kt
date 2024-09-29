package com.example.domain.characters

data class Characters(
    var info: Info,
    var characterList: List<Character>
)

data class Info(
    var numberOfCharacters: Int,
    var numberOfPages: Int
)

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val isInFavourites: Boolean = false
)