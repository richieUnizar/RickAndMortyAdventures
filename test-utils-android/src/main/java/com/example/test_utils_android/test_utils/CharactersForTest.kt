package com.example.test_utils_android.test_utils

import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.domain.model.Location
import com.example.domain.model.Origin


fun createCharacters() = Characters(
    info = Info(1, 1),
    characterList = listOf(createCharacter())
)

fun createCharacter() = Character(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    type = null,
    gender = "Male",
    origin = Origin(name = "Earth", url = "url"),
    location = Location(name = "Earth", url = "url"),
    image = "image",
    episode = listOf("episode 1"),
    url = "url",
    created = "2017-11-04",
    isInFavourites = false
)

fun generateCharacter(numberOfPages: Int, numberOfCharacters: Int) = Characters(
    info = Info(numberOfCharacters, numberOfPages),
    characterList = generateCharacterList(numberOfCharacters)
)

private fun generateCharacterList(numberOfItems: Int): List<Character> {
    val characterList = mutableListOf<Character>()

    for (i in 1..numberOfItems) {
        characterList.add(
            Character(
                id = i,
                name = "Character $i",
                status = "Alive",
                species = "Alien",
                image = "image$i",
                type = "type",
                gender = "gender",
                origin = Origin("", ""),
                location = Location("", ""),
                episode = emptyList(),
                url = "url",
                created = "created",
                isInFavourites = false,
            )
        )
    }

    return characterList
}