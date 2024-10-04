package com.example.test_utils_android.test_utils

import com.example.domain.model.Character
import com.example.domain.model.Location
import com.example.domain.model.Origin

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