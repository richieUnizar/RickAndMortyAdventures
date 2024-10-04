package com.example.presentation_character_details

import com.example.domain.model.Character
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

data class DetailsDisplay(
    val hasError: Boolean,
    val characterDisplay: CharacterDisplay? = null
)

data class CharacterDisplay(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val image: String,
    val created: String,
    val origin: String,
    val location: String,
)

fun Character.toDisplay() = CharacterDisplay(
    id = this.id,
    name = this.name,
    gender = this.gender,
    status = this.status,
    species = this.species,
    image = this.image,
    created = formatDateTime(this.created),
    origin = this.origin.name,
    location = this.location.name
)


fun formatDateTime(dateString: String): String {
    val dateTime = DateTime(dateString)
    val formatter = DateTimeFormat.forPattern("dd MMMM yyyy, HH:mm:ss")
    return dateTime.toString(formatter)
}