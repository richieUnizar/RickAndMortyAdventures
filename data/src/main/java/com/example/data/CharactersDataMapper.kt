package com.example.data

import com.example.data_source_rest.model.CharacterDTO
import com.example.data_source_rest.model.CharactersDTO
import com.example.data_source_rest.model.InfoDTO
import com.example.domain.characters.Characters
import com.example.domain.characters.Info
import com.example.domain.characters.Character

fun CharactersDTO.toDomain() = Characters(
    info = this.info.toDomain(),
    characterList = this.results.map { it.toDomain() }
)

fun InfoDTO.toDomain() = Info(
    numberOfCharacters = this.count,
    numberOfPages = this.pages
)

fun CharacterDTO.toDomain() = Character(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    image = this.image
)