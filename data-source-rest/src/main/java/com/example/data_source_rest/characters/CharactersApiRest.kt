package com.example.data_source_rest.characters

import com.example.data_source_rest.model.CharactersDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApiRest {

    @GET("character")
    fun getCharacters(@Query("page") page: Int?): Call<CharactersDTO>

}