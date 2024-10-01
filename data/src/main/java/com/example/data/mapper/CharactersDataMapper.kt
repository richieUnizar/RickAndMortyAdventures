package com.example.data.mapper

import com.example.data_source_rest.model.CharacterDTO
import com.example.data_source_rest.model.CharactersDTO
import com.example.data_source_rest.model.InfoDTO
import com.example.domain.model.Characters
import com.example.domain.model.Info
import com.example.domain.model.Character
import com.example.domain.model.Location
import com.example.domain.model.Origin

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
    type = this.type,
    gender = this.gender,
    origin = Origin(name = this.origin.name, url = this.origin.url),
    location = Location(name = this.location.name, url = this.location.url),
    image = this.image,
    episode = this.episode,
    url = this.url,
    created = this.created,
    isInFavourites = false
)
