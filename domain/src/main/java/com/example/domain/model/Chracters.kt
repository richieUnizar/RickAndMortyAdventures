package com.example.domain.model

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
    val type: String?,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    val isInFavourites: Boolean = false,
)

data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)