package com.example.test_utils_android.test_utils

import com.example.domain.Favourites.model.FavouriteCharacter

fun createFavouriteCharacter() = FavouriteCharacter(
    id = 1,
    name = "Rick Sanchez",
    status = "Alive",
    species = "Human",
    image = "image_url"
)
