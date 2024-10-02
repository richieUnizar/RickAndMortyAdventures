package com.example.data.utils

import com.example.data_source_rest.model.CharacterDTO
import com.example.data_source_rest.model.CharactersDTO
import com.example.data_source_rest.model.InfoDTO
import com.example.data_source_rest.model.LocationDTO
import com.example.domain.model.Character
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.domain.model.Location
import com.example.domain.model.Origin

object TestData {

    val CHARACTER_DTO = CharacterDTO(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = LocationDTO(name = "Earth (C-137)", url = "url"),
        location = LocationDTO(name = "Earth (Replacement Dimension)", url = "url"),
        image = "image",
        episode = emptyList(),
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )

    val CHARACTERS_DTO = CharactersDTO(
        info = InfoDTO(1, 1, null, null),
        results = listOf(CHARACTER_DTO)
    )

    val EXPECTED_CHARACTER = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Origin(name = "Earth (C-137)", url = "url"),
        location = Location(name = "Earth (Replacement Dimension)", url = "url"),
        image = "image",
        episode = emptyList(),
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )

    val EXPECTED_CHARACTERS = Characters(
        info = Info(1, 1),
        characterList = listOf(EXPECTED_CHARACTER)
    )

}