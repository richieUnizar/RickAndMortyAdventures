package com.example.data_source_rest.model

import com.google.gson.annotations.SerializedName

data class CharactersDTO(
    @SerializedName("info")
    val info: InfoDTO,
    @SerializedName("results")
    val results: List<CharacterDTO>
)

data class InfoDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)

data class CharacterDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String?,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: LocationDTO,
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)

data class LocationDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
