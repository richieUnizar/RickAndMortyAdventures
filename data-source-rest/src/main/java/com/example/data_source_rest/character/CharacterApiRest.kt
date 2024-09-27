package com.example.data_source_rest.character

import com.example.data_source_rest.model.CharacterDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApiRest {

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<CharacterDTO>
}