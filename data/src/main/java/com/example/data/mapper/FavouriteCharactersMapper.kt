package com.example.data.mapper

import com.example.data_source_room.data.CharacterEntity
import com.example.domain.Favourites.model.FavouriteCharacter

fun List<CharacterEntity>.toDomain() = this.map { entity ->
    FavouriteCharacter(
        id = entity.id,
        name = entity.name,
        status = entity.status,
        species = entity.species,
        image = entity.image
    )
}